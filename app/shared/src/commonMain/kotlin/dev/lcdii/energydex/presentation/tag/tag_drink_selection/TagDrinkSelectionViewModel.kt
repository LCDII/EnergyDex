package dev.lcdii.energydex.presentation.tag.tag_drink_selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.lcdii.energydex.presentation.energydrink.energydrink_section.components.EnergyDrinkListSortOptions
import dev.lcdii.energydex.core.domain.onError
import dev.lcdii.energydex.core.domain.onSuccess
import dev.lcdii.energydex.core.presentation.toUiText
import dev.lcdii.energydex.domain.energydrink.usecase.ObserveEnergyDrinksUseCase
import dev.lcdii.energydex.domain.energydrink.usecase.ObserveEnergyDrinksForTagUseCase
import dev.lcdii.energydex.domain.energydrink.usecase.UpdateEnergyDrinkTagRelationsUseCase
import dev.lcdii.energydex.domain.tag.usecase.GetTagByIdUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TagDrinkSelectionViewModel(
    private val tagId: Long,
    private val getTagById: GetTagByIdUseCase,
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
        loadTag()
        loadSelectedDrinkIds()
        observeDrinks()
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
            TagDrinkSelectionAction.OnSortButtonClick -> _state.update {
                it.copy(isSortMenuVisible = !it.isSortMenuVisible)
            }
            is TagDrinkSelectionAction.OnSortOptionSelected -> {
                val current = _state.value.sortOption
                val selectedField = action.option.name.substringBefore("_")
                val currentField = current.name.substringBefore("_")
                val newOption = if (selectedField == currentField) {
                    val direction = if (current.name.endsWith("_ASC")) "DESC" else "ASC"
                    EnergyDrinkListSortOptions.valueOf("${selectedField}_$direction")
                } else {
                    val direction = if (selectedField == "TITLE") "ASC" else "DESC"
                    EnergyDrinkListSortOptions.valueOf("${selectedField}_$direction")
                }
                _state.update {
                    it.copy(sortOption = newOption, isSortMenuVisible = false)
                }
            }
            TagDrinkSelectionAction.OnSaveClick -> save()
        }
    }

    private fun loadTag() = viewModelScope.launch {
        getTagById(tagId)
            .onSuccess { tag -> _state.update { it.copy(currentTag = tag) } }
            .onError { error -> showError(error) }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeDrinks() {
        _state
            .map { it.searchQuery to it.sortOption }
            .distinctUntilChanged()
            .debounce(300L)
            .flatMapLatest { (query, sortOption) ->
                observeEnergyDrinks(query, sortOption)
            }
            .onStart {
                _state.update { it.copy(isLoading = true, errorMessage = null) }
            }
            .onEach { drinks ->
                _state.update {
                    it.copy(isLoading = false, energyDrinks = drinks, errorMessage = null)
                }
            }
            .catch { error ->
                _state.update {
                    it.copy(isLoading = false, energyDrinks = emptyList(), errorMessage = error.toUiText())
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadSelectedDrinkIds() = viewModelScope.launch {
        observeEnergyDrinksForTag(tagId)
            .collect { selectedDrinks ->
                val selectedIds = selectedDrinks.map { it.id }.toSet()
                _state.update {
                    it.copy(
                        selectedDrinkIds = selectedIds,
                        initialSelectedDrinkIds = selectedIds
                    )
                }
            }
    }

    private fun save() = viewModelScope.launch {
        val current = _state.value
        if (current.isSaving) return@launch
        _state.update { it.copy(isSaving = true) }
        updateRelations(
            tagId = tagId,
            previousDrinkIds = current.initialSelectedDrinkIds,
            selectedDrinkIds = current.selectedDrinkIds
        ).onSuccess {
            _state.update { it.copy(isSaving = false, isSaved = true) }
        }.onError { error ->
            _state.update { it.copy(isSaving = false, errorMessage = error.toUiText()) }
        }
    }

    private fun showError(error: dev.lcdii.energydex.core.domain.DataError.Local) {
        _state.update { it.copy(isLoading = false, errorMessage = error.toUiText()) }
    }
}
