package com.example.energydex.presentation.tag.tag_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.energydex.presentation.energydrink.energydrink_section.components.EnergyDrinkListSortOptions
import com.example.energydex.core.domain.onError
import com.example.energydex.core.domain.onSuccess
import com.example.energydex.core.presentation.toUiText
import com.example.energydex.domain.energydrink.usecase.ObserveEnergyDrinksForTagUseCase
import com.example.energydex.domain.energydrink.usecase.UpdateEnergyDrinkTagRelationsUseCase
import com.example.energydex.domain.tag.usecase.DeleteTagUseCase
import com.example.energydex.domain.tag.usecase.GetTagByIdUseCase
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

class TagDetailViewModel(
    private val id: Long,
    private val getTagById: GetTagByIdUseCase,
    private val observeEnergyDrinksForTag: ObserveEnergyDrinksForTagUseCase,
    private val deleteTag: DeleteTagUseCase,
    private val updateRelations: UpdateEnergyDrinkTagRelationsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(TagDetailState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    init {
        loadCurrentTag()
        loadTaggedDrinkIds()
        observeDrinks()
    }

    fun onAction(action: TagDetailAction) {
        when (action) {
            TagDetailAction.OnBackClick -> Unit
            TagDetailAction.OnEditClick -> Unit
            TagDetailAction.OnAddDrinksClick -> Unit
            is TagDetailAction.OnEnergyDrinkNavigateClick -> Unit
            is TagDetailAction.OnEnergyDrinkHold -> _state.update {
                it.copy(
                    isSelectionMode = true,
                    selectedEnergyDrinkIds = it.selectedEnergyDrinkIds + action.energyDrink.id
                )
            }
            is TagDetailAction.OnSelectEnergyDrink -> _state.update {
                val selected = it.selectedEnergyDrinkIds.toMutableSet()
                if (!selected.add(action.energyDrink.id)) selected.remove(action.energyDrink.id)
                it.copy(
                    selectedEnergyDrinkIds = selected,
                    isSelectionMode = selected.isNotEmpty()
                )
            }
            TagDetailAction.OnCancelSelectionClick -> clearSelection()
            TagDetailAction.OnRemoveSelectedClick -> removeSelected()
            is TagDetailAction.OnSearchQueryChange -> _state.update {
                it.copy(searchQuery = action.query)
            }
            is TagDetailAction.OnTabSelected -> _state.update {
                it.copy(selectedTabIndex = action.tab)
            }
            TagDetailAction.OnSortButtonClick -> _state.update {
                it.copy(isSortMenuVisible = !it.isSortMenuVisible)
            }
            is TagDetailAction.OnSortOptionSelected -> {
                val current = _state.value.sortOption
                val selectedField = action.option.name.substringBefore("_")
                val currentField = current.name.substringBefore("_")
                val newOption = if (selectedField == currentField) {
                    val direction =
                        if (current.name.substringAfter("_") == "ASC") "DESC" else "ASC"
                    EnergyDrinkListSortOptions.valueOf("${selectedField}_$direction")
                } else {
                    val direction = if (selectedField == "TITLE") "ASC" else "DESC"
                    EnergyDrinkListSortOptions.valueOf("${selectedField}_$direction")
                }
                _state.update {
                    it.copy(sortOption = newOption, isSortMenuVisible = false)
                }
            }
            TagDetailAction.OnDeleteClick -> _state.update {
                it.copy(isDeleteDialogVisible = true, errorMessage = null)
            }
            TagDetailAction.OnConfirmDeleteClick -> viewModelScope.launch {
                _state.update { it.copy(isDeleteDialogVisible = false) }
                deleteTag(id).onSuccess {
                    _state.update { it.copy(isDeleted = true) }
                }.onError { error -> showError(error) }
            }
            TagDetailAction.OnDeclineDeleteClick -> _state.update {
                it.copy(isDeleteDialogVisible = false)
            }
        }
    }

    private fun loadCurrentTag() = viewModelScope.launch {
        getTagById(id).onSuccess { tag ->
            _state.update {
                it.copy(currentTag = tag, errorMessage = null)
            }
        }.onError { error -> showError(error) }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeDrinks() {
        _state
            .map { it.searchQuery to it.sortOption }
            .distinctUntilChanged()
            .debounce(300L)
            .flatMapLatest { (query, sortOption) ->
                observeEnergyDrinksForTag(id, query, sortOption)
            }
            .onStart {
                _state.update { it.copy(isLoading = true, errorMessage = null) }
            }
            .onEach { drinks ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        energyDrinks = drinks,
                        errorMessage = null
                    )
                }
            }
            .catch { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        energyDrinks = emptyList(),
                        errorMessage = error.toUiText()
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadTaggedDrinkIds() {
        observeEnergyDrinksForTag(id)
            .onEach { drinks ->
                _state.update { it.copy(taggedEnergyDrinkIds = drinks.map { drink -> drink.id }.toSet()) }
            }
            .launchIn(viewModelScope)
    }

    private fun showError(error: com.example.energydex.core.domain.DataError.Local) {
        _state.update { it.copy(isLoading = false, errorMessage = error.toUiText()) }
    }

    private fun clearSelection() {
        _state.update {
            it.copy(
                isSelectionMode = false,
                selectedEnergyDrinkIds = emptySet(),
                isBulkOperationRunning = false
            )
        }
    }

    private fun removeSelected() {
        val selectedIds = _state.value.selectedEnergyDrinkIds
        if (selectedIds.isEmpty() || _state.value.isBulkOperationRunning) return

        viewModelScope.launch {
            _state.update { it.copy(isBulkOperationRunning = true) }
            val currentIds = _state.value.taggedEnergyDrinkIds
            updateRelations(
                tagId = id,
                previousDrinkIds = currentIds,
                selectedDrinkIds = currentIds - selectedIds
            ).onSuccess {
                clearSelection()
            }.onError { error ->
                _state.update {
                    it.copy(isBulkOperationRunning = false, errorMessage = error.toUiText())
                }
            }
        }
    }
}
