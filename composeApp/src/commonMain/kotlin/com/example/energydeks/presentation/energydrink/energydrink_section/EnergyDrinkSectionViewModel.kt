package com.example.energydeks.presentation.energydrink.energydrink_section


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.energydeks.domain.energydrink.usecase.CreateEnergyDrinkUseCase
import com.example.energydeks.domain.energydrink.usecase.DeleteEnergyDrinkUseCase
import com.example.energydeks.domain.energydrink.usecase.GetAllEnergyDrinksUseCase
import com.example.energydeks.domain.energydrink.usecase.UpdateEnergyDrinkUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class EnergyDrinkSectionViewModel (
    private val createEnergyDrink: CreateEnergyDrinkUseCase,
    private val deleteEnergyDrink: DeleteEnergyDrinkUseCase,
    private val editEnergyDrin: UpdateEnergyDrinkUseCase,
    private val getAllEnergyDrinks: GetAllEnergyDrinksUseCase
): ViewModel() {
    private val _state = MutableStateFlow(EnergyDrinkSectionState())

    val state = _state.stateIn(
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
            is EnergyDrinkSectionAction.OnEnergyDrinkClick->{

            }
            is EnergyDrinkSectionAction.OnTabSelected->{
                _state.update { it.copy(
                    selectedTabIndex = action.index
                ) }
            }
        }
    }
}