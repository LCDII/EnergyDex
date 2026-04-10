package com.example.energydeks.presentation.energydrink.energydrink_section

import com.example.energydeks.core.presentation.UiText
import com.example.energydeks.domain.energydrink.model.EnergyDrink
import com.example.energydeks.presentation.energydrink.energydrink_section.components.EnergyDrinkSectionTab

data class EnergyDrinkSectionState(
    val searchQuery: String = "",
    val searchResult:List<EnergyDrink> = emptyList(),
    val isLoading: Boolean = true,
    val selectedTabIndex: EnergyDrinkSectionTab = EnergyDrinkSectionTab.SQUARED,
    val errorMessage: UiText? = null
)
