package com.example.energydex.presentation.energydrink.energydrink_section


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.energydex.core.domain.EnergyDrinkListSortOptions
import com.example.energydex.core.domain.onError
import com.example.energydex.core.domain.onSuccess
import com.example.energydex.core.presentation.toUiText
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.domain.energydrink.usecase.DeleteEnergyDrinkUseCase
import com.example.energydex.domain.energydrink.usecase.GetAllEnergyDrinksUseCase
import com.example.energydex.domain.energydrink.usecase.SearchEnergyDrinksUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.copy

class EnergyDrinkSectionViewModel (
    private val deleteEnergyDrink: DeleteEnergyDrinkUseCase,
    private val getAllEnergyDrinks: GetAllEnergyDrinksUseCase,
    private val searchEnergyDrinks: SearchEnergyDrinksUseCase
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

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery(){
        _state
            .map { it.searchQuery to it.sortOption}
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { (query, sortOption) ->
                searchJob?.cancel()
                searchJob = loadEnergyDrinks(
                    query,
                    sortOption
                )
            }
            .launchIn(viewModelScope)
    }

    private fun loadEnergyDrinks(
        query: String,
        sortOption: EnergyDrinkListSortOptions
    ) = viewModelScope.launch {

        _state.update {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }

        val result =
            if (query.isBlank()) {
                getAllEnergyDrinks(sortOption)
            } else {
                searchEnergyDrinks(query, sortOption)
            }

        result
            .onSuccess { list ->
                _state.update {
                    it.copy(
                        searchResult = list,
                        isLoading = false
                    )
                }
            }
            .onError { error ->
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