package com.example.energydeks.energydrink.presentation.energydrink_list

import com.example.energydeks.core.presentation.UiText
import com.example.energydeks.energydrink.domain.EnergyDrink

data class EnergyDrinkListState(
    val searchQuery: String = "",
    val searchResult:List<EnergyDrink> = emptyList(),
    val isLoading: Boolean = true,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)
