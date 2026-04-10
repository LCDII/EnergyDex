package com.example.energydeks.presentation.energydrink.energydrink_section

import com.example.energydeks.core.presentation.UiText
import com.example.energydeks.domain.energydrink.model.EnergyDrink

data class EnergyDrinkSectionState(
    val searchQuery: String = "",
    val searchResult:List<EnergyDrink> = emptyList(),
    val isLoading: Boolean = true,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)
