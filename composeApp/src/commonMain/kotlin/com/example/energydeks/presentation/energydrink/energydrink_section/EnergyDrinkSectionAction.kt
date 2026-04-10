package com.example.energydeks.presentation.energydrink.energydrink_section

import com.example.energydeks.domain.energydrink.model.EnergyDrink
import com.example.energydeks.presentation.energydrink.energydrink_section.components.EnergyDrinkSectionTab

sealed interface EnergyDrinkSectionAction {
    data class OnSearchQueryChange(val query: String) : EnergyDrinkSectionAction
    data class OnEnergyDrinkClick(val energyDrink: EnergyDrink) : EnergyDrinkSectionAction
    data class OnTabSelected(val index: EnergyDrinkSectionTab): EnergyDrinkSectionAction //longed or squared item will be
    //TODO on sortchange
}