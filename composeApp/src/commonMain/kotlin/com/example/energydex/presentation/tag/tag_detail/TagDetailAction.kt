package com.example.energydex.presentation.tag.tag_detail

import com.example.energydex.presentation.energydrink.energydrink_section.components.EnergyDrinkListSortOptions
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.presentation.shared.components.EnergyDrinkSectionTab

sealed interface TagDetailAction {
    data object OnBackClick : TagDetailAction
    data object OnEditClick : TagDetailAction
    data object OnAddDrinksClick : TagDetailAction
    data class OnEnergyDrinkNavigateClick(val energyDrink: EnergyDrink) : TagDetailAction
    data class OnSearchQueryChange(val query: String) : TagDetailAction
    data class OnTabSelected(val tab: EnergyDrinkSectionTab) : TagDetailAction
    data object OnSortButtonClick : TagDetailAction
    data class OnSortOptionSelected(val option: EnergyDrinkListSortOptions) : TagDetailAction
    data object OnDeleteClick : TagDetailAction
    data object OnConfirmDeleteClick : TagDetailAction
    data object OnDeclineDeleteClick : TagDetailAction
}
