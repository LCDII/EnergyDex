package com.example.energydeks.presentation.energydrink.energydrink_section

import com.example.energydeks.core.presentation.UiText
import com.example.energydeks.domain.energydrink.model.EnergyDrink
import com.example.energydeks.domain.tag.model.Tag
import com.example.energydeks.core.domain.EnergyDrinkListSortOptions
import com.example.energydeks.presentation.energydrink.energydrink_section.components.EnergyDrinkSectionTab
import kotlin.time.Instant

data class EnergyDrinkSectionState(
    val searchQuery: String = "",
    val searchResult: List<EnergyDrink> = emptyList(),
    val selectedEnergyDrinkIds: List<Long> = emptyList(),
    val isSortMode: Boolean = false,
    val sortOption: EnergyDrinkListSortOptions = EnergyDrinkListSortOptions.RATING_DESC,
    val isLoading: Boolean = true,
    val isSelectionMode: Boolean = false,
    val selectedTabIndex: EnergyDrinkSectionTab = EnergyDrinkSectionTab.SQUARED,
    val errorMessage: UiText? = null
)
