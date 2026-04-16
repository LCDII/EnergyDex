package com.example.energydeks.presentation.energydrink.energydrink_section


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.energydeks.core.domain.onError
import com.example.energydeks.core.domain.onSuccess
import com.example.energydeks.core.presentation.UiText
import com.example.energydeks.core.presentation.toUiText
import com.example.energydeks.domain.energydrink.usecase.CreateEnergyDrinkUseCase
import com.example.energydeks.domain.energydrink.usecase.DeleteEnergyDrinkUseCase
import com.example.energydeks.domain.energydrink.usecase.GetAllEnergyDrinksUseCase
import com.example.energydeks.domain.energydrink.usecase.SearchEnergyDrinksUseCase
import com.example.energydeks.domain.energydrink.usecase.UpdateEnergyDrinkUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EnergyDrinkSectionViewModel (
    private val deleteEnergyDrink: DeleteEnergyDrinkUseCase,
    private val getAllEnergyDrinks: GetAllEnergyDrinksUseCase,
    private val searchEnergyDrinksUseCase: SearchEnergyDrinksUseCase
): ViewModel() {
    private val _state = MutableStateFlow(EnergyDrinkSectionState())
    private var searchJob: Job? = null
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
            is EnergyDrinkSectionAction.OnCreateButtonClick->{
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
                    isSortMode = !it.isSortMode
                ) }
            }
            is EnergyDrinkSectionAction.OnSortOptionSelected->{
                _state.update { it.copy(
                    sortOption = action.option
                ) }
            }
            is EnergyDrinkSectionAction.OnTabSelected->{
                _state.update { it.copy(
                    selectedTabIndex = action.index
                ) }
            }
        }
    }

    private fun observeSearchQuery(){
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                searchJob?.cancel()
                searchJob = searchEnergyDrinks(query)
            }
            .launchIn(viewModelScope)
    }

    private fun searchEnergyDrinks(query: String) = viewModelScope.launch {
        _state.update { it.copy(
            isLoading = true
        ) }

        searchEnergyDrinksUseCase.invoke(
            query,
            _state.value.sortOption
        )
            .onSuccess { searchResult ->
            _state.update { it.copy(
                searchResult = searchResult,
                isLoading = false,
                errorMessage = null,
            ) }
        }
            .onError { error->
                _state.update {
                    it.copy(
                        searchResult = emptyList(),
                        isLoading = false,
                        errorMessage = error.toUiText()
                    )
                }
            }
    }
}