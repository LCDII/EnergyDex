package com.example.energydex.presentation.tag.tag_drink_selection

import com.example.energydex.core.presentation.UiText
import com.example.energydex.presentation.energydrink.energydrink_section.components.EnergyDrinkListSortOptions
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.domain.tag.model.Tag
import com.example.energydex.presentation.shared.components.EnergyDrinkSectionTab

data class TagDrinkSelectionState(
    val isLoading: Boolean = true,
    val energyDrinks: List<EnergyDrink> = emptyList(),
    val searchQuery: String = "",
    val currentTag: Tag? = null,
    val selectedDrinkIds: Set<Long> = emptySet(),
    val initialSelectedDrinkIds: Set<Long> = emptySet(),
    val selectedTab: EnergyDrinkSectionTab = EnergyDrinkSectionTab.SQUARED,
    val sortOption: EnergyDrinkListSortOptions = EnergyDrinkListSortOptions.RATING_DESC,
    val isSortMenuVisible: Boolean = false,
    val isSaved: Boolean = false,
    val isSaving: Boolean = false,
    val errorMessage: UiText? = null
)
