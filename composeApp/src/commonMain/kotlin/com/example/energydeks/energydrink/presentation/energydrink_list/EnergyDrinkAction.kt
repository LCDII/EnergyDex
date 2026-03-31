package com.example.energydeks.energydrink.presentation.energydrink_list

import com.example.energydeks.energydrink.domain.EnergyDrink

interface EnergyDrinkAction {
    data class OnSearchQueryChange(val query: String) : EnergyDrinkAction
    data class OnEnergyDrinkClick(val energyDrink: EnergyDrink) : EnergyDrinkAction
    data class OnTabSelected(val index: Int): EnergyDrinkAction
}