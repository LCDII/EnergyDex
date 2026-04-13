package com.example.energydeks.presentation.energydrink.energydrink_section

import com.example.energydeks.domain.energydrink.model.EnergyDrink
import com.example.energydeks.presentation.energydrink.energydrink_section.components.EnergyDrinkListSortOptions
import com.example.energydeks.presentation.energydrink.energydrink_section.components.EnergyDrinkSectionTab

sealed interface EnergyDrinkSectionAction {
    data class OnSearchQueryChange(val query: String) : EnergyDrinkSectionAction
    data class OnEnergyDrinkNavigateClick(val energyDrink: EnergyDrink) : EnergyDrinkSectionAction
    data class OnTabSelected(val index: EnergyDrinkSectionTab): EnergyDrinkSectionAction //longed or squared item will be
    data class OnCreateButtonClick(val index: Int): EnergyDrinkSectionAction

//    data class OnEnergyDrinkHold(val energyDrink: EnergyDrink): EnergyDrinkSectionAction
//
//    data object OnCancelSelectionClick : EnergyDrinkSectionAction
//
//    data class OnSelectEnergyDrink(val energyDrink: EnergyDrink): EnergyDrinkSectionAction
//
//    data class OnUpdateOptionClick(val index: Int): EnergyDrinkSectionAction
//
//    data class OnDeleteOptionClick(val index: Int): EnergyDrinkSectionAction

    data class OnSortButtonClick(val index: Int): EnergyDrinkSectionAction

    data class OnSortOptionSelected(val option: EnergyDrinkListSortOptions): EnergyDrinkSectionAction
}