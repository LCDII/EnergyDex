package com.example.energydeks.presentation.energydrink.energydrink_list

import com.example.energydeks.domain.energydrink.model.EnergyDrink

interface EnergyDrinkAction {
    data class OnSearchQueryChange(val query: String) : EnergyDrinkAction
    data class OnEnergyDrinkClick(val energyDrink: EnergyDrink) : EnergyDrinkAction
    data class OnTabSelected(val index: Int): EnergyDrinkAction
}