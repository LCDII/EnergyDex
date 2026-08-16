package com.example.energydex.presentation.energydrink.energydrink_section


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.energydex.presentation.energydrink.energydrink_section.components.EnergyDrinkListSortOptions
import com.example.energydex.core.presentation.toUiText
import com.example.energydex.core.domain.Result
import com.example.energydex.domain.energydrink.usecase.AddTagsToEnergyDrinksUseCase
import com.example.energydex.domain.energydrink.usecase.DeleteEnergyDrinkUseCase
import com.example.energydex.domain.energydrink.usecase.ObserveEnergyDrinksUseCase
import com.example.energydex.domain.tag.usecase.ObserveTagsUseCase
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

class EnergyDrinkSectionViewModel (
    private val observeEnergyDrinks: ObserveEnergyDrinksUseCase,
    private val deleteEnergyDrink: DeleteEnergyDrinkUseCase,
    private val addTagsToEnergyDrinks: AddTagsToEnergyDrinksUseCase,
    private val observeTags: ObserveTagsUseCase,
): ViewModel() {
    private val _state = MutableStateFlow(EnergyDrinkSectionState())
    val state = _state
        .onStart {
            observeSearchQuery()
            observeAllTags()
        }
        .stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
        )

    fun onAction(action: EnergyDrinkSectionAction){
        when(action) {
            is EnergyDrinkSectionAction.OnSearchQueryChange->{
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }
            is EnergyDrinkSectionAction.OnEnergyDrinkNavigateClick->{
                //navigation only; ui responsible for nav;
            }
            is EnergyDrinkSectionAction.OnAddEnergyDrink->{
                //navigation only; ui responsible for nav;
            }
            is EnergyDrinkSectionAction.OnEnergyDrinkHold -> {
                _state.update {
                    it.copy(
                        isSelectionMode = true,
                        selectedEnergyDrinkIds = (it.selectedEnergyDrinkIds + action.energyDrink.id).distinct()
                    )
                }
            }
            is EnergyDrinkSectionAction.OnSelectEnergyDrink -> {
                _state.update {
                    val selected = it.selectedEnergyDrinkIds.toMutableList()
                    if (!selected.remove(action.energyDrink.id)) {
                        selected += action.energyDrink.id
                    }
                    it.copy(
                        selectedEnergyDrinkIds = selected,
                        isSelectionMode = selected.isNotEmpty()
                    )
                }
            }
            EnergyDrinkSectionAction.OnCancelSelectionClick -> clearSelection()
            EnergyDrinkSectionAction.OnDeleteSelectedClick -> {
                if (_state.value.selectedEnergyDrinkIds.isNotEmpty()) {
                    _state.update { it.copy(isDeleteDialogVisible = true) }
                }
            }
            EnergyDrinkSectionAction.OnDismissDeleteDialogClick -> {
                _state.update { it.copy(isDeleteDialogVisible = false) }
            }
            EnergyDrinkSectionAction.OnConfirmDeleteSelectedClick -> deleteSelected()
            EnergyDrinkSectionAction.OnTagSelectedClick -> {
                if (_state.value.selectedEnergyDrinkIds.isNotEmpty()) {
                    _state.update { it.copy(isTagDialogVisible = true, selectedTagIds = emptySet()) }
                }
            }
            is EnergyDrinkSectionAction.OnToggleTag -> {
                _state.update {
                    val selected = it.selectedTagIds.toMutableSet()
                    if (!selected.add(action.tagId)) selected.remove(action.tagId)
                    it.copy(selectedTagIds = selected)
                }
            }
            EnergyDrinkSectionAction.OnDismissTagDialogClick -> {
                _state.update { it.copy(isTagDialogVisible = false, selectedTagIds = emptySet()) }
            }
            EnergyDrinkSectionAction.OnConfirmTagSelectedClick -> addSelectedTags()
            is EnergyDrinkSectionAction.OnSortButtonClick->{
                _state.update { it.copy(
                    isSortMenuVisible = !it.isSortMenuVisible
                ) }
            }
            is EnergyDrinkSectionAction.OnSortOptionSelected->{
                val current = _state.value.sortOption
                val selectedField = action.option.name.substringBefore("_")
                val currentField = current.name.substringBefore("_")
                val newOption = if (selectedField == currentField) {
                    val newDirection =
                        if (current.name.substringAfter("_") == "ASC") "DESC" else "ASC"
                    EnergyDrinkListSortOptions.valueOf("${selectedField}_$newDirection")
                } else {
                    val defaultDirection = if (selectedField == "TITLE") "ASC" else "DESC"
                    EnergyDrinkListSortOptions.valueOf("${selectedField}_$defaultDirection")
                }
                _state.update {
                    it.copy(
                        sortOption = newOption,
                        isSortMenuVisible = false
                    )
                }
            }
            is EnergyDrinkSectionAction.OnTabSelected->{
                _state.update { it.copy(
                    selectedTabIndex = action.index
                ) }
            }
        }
    }

    private fun observeAllTags() {
        observeTags()
            .onEach { tags -> _state.update { it.copy(tags = tags) } }
            .launchIn(viewModelScope)
    }

    private fun clearSelection() {
        _state.update {
            it.copy(
                isSelectionMode = false,
                selectedEnergyDrinkIds = emptyList(),
                isDeleteDialogVisible = false,
                isTagDialogVisible = false,
                selectedTagIds = emptySet()
            )
        }
    }

    private fun deleteSelected() {
        val ids = _state.value.selectedEnergyDrinkIds.toList()
        viewModelScope.launch {
            _state.update { it.copy(isBulkOperationRunning = true) }
            for (id in ids) {
                when (deleteEnergyDrink(id)) {
                    is Result.Success -> Unit
                    is Result.Error -> {
                        _state.update { it.copy(isBulkOperationRunning = false) }
                        return@launch
                    }
                }
            }
            _state.update { it.copy(isBulkOperationRunning = false) }
            clearSelection()
        }
    }

    private fun addSelectedTags() {
        val state = _state.value
        if (state.selectedEnergyDrinkIds.isEmpty() || state.selectedTagIds.isEmpty()) return
        viewModelScope.launch {
            _state.update { it.copy(isBulkOperationRunning = true) }
            when (addTagsToEnergyDrinks(state.selectedEnergyDrinkIds.toSet(), state.selectedTagIds)) {
                is Result.Success -> {
                    _state.update { it.copy(isBulkOperationRunning = false) }
                    clearSelection()
                }
                is Result.Error -> _state.update { it.copy(isBulkOperationRunning = false) }
            }
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeSearchQuery() {
        _state
            .map { it.searchQuery to it.sortOption }
            .distinctUntilChanged()
            .debounce(300L)
            .flatMapLatest { (query, sortOption) ->
                observeEnergyDrinks(query, sortOption)
            }
            .onEach { list ->
                _state.update {
                    it.copy(
                        searchResult = list,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }
            .catch { error ->
                _state.update {
                    it.copy(
                        searchResult = emptyList(),
                        isLoading = false,
                        errorMessage = error.toUiText()
                    )
                }
            }
            .launchIn(viewModelScope)
    }


}
