package com.example.energydeks.presentation.energydrink.energydrink_section

import com.example.energydeks.domain.energydrink.model.EnergyDrink

sealed interface EnergyDrinkSectionAction {
    data class OnSearchQueryChange(val query: String) : EnergyDrinkSectionAction
    data class OnEnergyDrinkClick(val energyDrink: EnergyDrink) : EnergyDrinkSectionAction
    data class OnTabSelected(val index: Int): EnergyDrinkSectionAction
    //TODO on sortchange
}