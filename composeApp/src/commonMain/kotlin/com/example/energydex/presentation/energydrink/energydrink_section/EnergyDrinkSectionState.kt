package com.example.energydex.presentation.energydrink.energydrink_section

import com.example.energydex.core.presentation.UiText
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.core.domain.EnergyDrinkListSortOptions
import com.example.energydex.presentation.shared.components.EnergyDrinkSectionTab

data class EnergyDrinkSectionState(
    val searchQuery: String = "",
    val searchResult: List<EnergyDrink> = emptyList(),
    val selectedEnergyDrinkIds: List<Long> = emptyList(),
    val isSortMenuVisible: Boolean = false,
    val sortOption: EnergyDrinkListSortOptions = EnergyDrinkListSortOptions.DATE_DESC,
    val isLoading: Boolean = true,
    val isSelectionMode: Boolean = false,
    val selectedTabIndex: EnergyDrinkSectionTab = EnergyDrinkSectionTab.SQUARED,
    val errorMessage: UiText? = null
)
