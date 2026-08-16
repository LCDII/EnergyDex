package com.example.energydex.presentation.energydrink.energydrink_section

import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.presentation.energydrink.energydrink_section.components.EnergyDrinkListSortOptions
import com.example.energydex.presentation.shared.components.EnergyDrinkSectionTab

sealed interface EnergyDrinkSectionAction {
    data class OnSearchQueryChange(val query: String) : EnergyDrinkSectionAction
    data class OnEnergyDrinkNavigateClick(val energyDrink: EnergyDrink) : EnergyDrinkSectionAction
    data class OnTabSelected(val index: EnergyDrinkSectionTab): EnergyDrinkSectionAction //longed or squared item will be
    data object OnAddEnergyDrink: EnergyDrinkSectionAction

    data class OnEnergyDrinkHold(val energyDrink: EnergyDrink): EnergyDrinkSectionAction
    data class OnSelectEnergyDrink(val energyDrink: EnergyDrink): EnergyDrinkSectionAction
    data object OnCancelSelectionClick : EnergyDrinkSectionAction
    data object OnDeleteSelectedClick : EnergyDrinkSectionAction
    data object OnConfirmDeleteSelectedClick : EnergyDrinkSectionAction
    data object OnDismissDeleteDialogClick : EnergyDrinkSectionAction
    data object OnTagSelectedClick : EnergyDrinkSectionAction
    data class OnToggleTag(val tagId: Long) : EnergyDrinkSectionAction
    data object OnConfirmTagSelectedClick : EnergyDrinkSectionAction
    data object OnDismissTagDialogClick : EnergyDrinkSectionAction

    data object OnSortButtonClick : EnergyDrinkSectionAction
    data class OnSortOptionSelected(val option: EnergyDrinkListSortOptions): EnergyDrinkSectionAction
}
