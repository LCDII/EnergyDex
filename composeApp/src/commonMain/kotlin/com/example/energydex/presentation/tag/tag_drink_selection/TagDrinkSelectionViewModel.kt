package com.example.energydex.presentation.tag.tag_drink_selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.energydex.core.domain.EnergyDrinkListSortOptions
import com.example.energydex.core.domain.onError
import com.example.energydex.core.domain.onSuccess
import com.example.energydex.core.presentation.toUiText
import com.example.energydex.domain.energydrink.usecase.ObserveEnergyDrinksForTagUseCase
import com.example.energydex.domain.energydrink.usecase.ObserveEnergyDrinksUseCase
import com.example.energydex.domain.energydrink.usecase.UpdateEnergyDrinkTagRelationsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TagDrinkSelectionViewModel(
    private val tagId: Long,
    private val observeEnergyDrinks: ObserveEnergyDrinksUseCase,
    private val observeEnergyDrinksForTag: ObserveEnergyDrinksForTagUseCase,
    private val updateRelations: UpdateEnergyDrinkTagRelationsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(TagDrinkSelectionState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    init {
        load()
    }

    fun onAction(action: TagDrinkSelectionAction) {
        when (action) {
            is TagDrinkSelectionAction.OnSearchQueryChange -> _state.update {
                it.copy(searchQuery = action.query)
            }
            is TagDrinkSelectionAction.OnDrinkToggle -> _state.update {
                val selected = it.selectedDrinkIds.toMutableSet()
                if (!selected.add(action.drinkId)) selected.remove(action.drinkId)
                it.copy(selectedDrinkIds = selected)
            }
            is TagDrinkSelectionAction.OnTabSelected -> _state.update {
                it.copy(selectedTab = action.tab)
            }
            TagDrinkSelectionAction.OnSaveClick -> save()
        }
    }

    private fun load() = viewModelScope.launch {
        try {
            val allDrinks = observeEnergyDrinks(
                sortOption = EnergyDrinkListSortOptions.RATING_DESC
            ).first()
            val selected = observeEnergyDrinksForTag(tagId).first()
            val selectedIds = selected.map { it.id }.toSet()
            _state.update {
                it.copy(
                    isLoading = false,
                    drinks = allDrinks,
                    selectedDrinkIds = selectedIds,
                    initialDrinkIds = selectedIds
                )
            }
        } catch (error: Throwable) {
            _state.update { it.copy(isLoading = false, errorMessage = error.toUiText()) }
        }
    }

    private fun save() = viewModelScope.launch {
        val current = _state.value
        if (current.isSaving) return@launch
        _state.update { it.copy(isSaving = true) }
        updateRelations(tagId, current.initialDrinkIds, current.selectedDrinkIds)
            .onSuccess { _state.update { it.copy(isSaving = false, isSaved = true) } }
            .onError { error ->
                _state.update { it.copy(isSaving = false, errorMessage = error.toUiText()) }
            }
    }
}
