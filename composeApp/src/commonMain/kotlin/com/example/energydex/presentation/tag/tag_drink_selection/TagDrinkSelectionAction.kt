package com.example.energydex.presentation.tag.tag_drink_selection

import com.example.energydex.core.domain.EnergyDrinkListSortOptions
import com.example.energydex.presentation.shared.components.EnergyDrinkSectionTab

sealed interface TagDrinkSelectionAction {
    data class OnSearchQueryChange(val query: String) : TagDrinkSelectionAction
    data class OnDrinkToggle(val drinkId: Long) : TagDrinkSelectionAction
    data class OnTabSelected(val tab: EnergyDrinkSectionTab) : TagDrinkSelectionAction
    data object OnSortButtonClick : TagDrinkSelectionAction
    data class OnSortOptionSelected(val option: EnergyDrinkListSortOptions) : TagDrinkSelectionAction
    data object OnSaveClick : TagDrinkSelectionAction
}
