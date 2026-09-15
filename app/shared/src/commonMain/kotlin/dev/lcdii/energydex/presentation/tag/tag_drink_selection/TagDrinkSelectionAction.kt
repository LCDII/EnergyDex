package dev.lcdii.energydex.presentation.tag.tag_drink_selection

import dev.lcdii.energydex.presentation.energydrink.energydrink_section.components.EnergyDrinkListSortOptions
import dev.lcdii.energydex.presentation.shared.components.EnergyDrinkSectionTab

sealed interface TagDrinkSelectionAction {
    data class OnSearchQueryChange(val query: String) : TagDrinkSelectionAction
    data class OnDrinkToggle(val drinkId: Long) : TagDrinkSelectionAction
    data class OnTabSelected(val tab: EnergyDrinkSectionTab) : TagDrinkSelectionAction
    data object OnSortButtonClick : TagDrinkSelectionAction
    data class OnSortOptionSelected(val option: EnergyDrinkListSortOptions) : TagDrinkSelectionAction
    data object OnSaveClick : TagDrinkSelectionAction
}
