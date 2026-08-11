package com.example.energydex.presentation.energydrink.energydrink_section


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.energydex.core.domain.EnergyDrinkListSortOptions
import com.example.energydex.core.presentation.toUiText
import com.example.energydex.domain.energydrink.usecase.ObserveEnergyDrinksUseCase
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
): ViewModel() {
    private val _state = MutableStateFlow(EnergyDrinkSectionState())
    val state = _state
        .onStart {
            observeSearchQuery()
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
//            is EnergyDrinkSectionAction.OnEnergyDrinkHold->{
//
//            }
//            is EnergyDrinkSectionAction.OnDeleteOptionClick->{
//
//            }
//            is EnergyDrinkSectionAction.OnUpdateOptionClick->{
//
//            }
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
