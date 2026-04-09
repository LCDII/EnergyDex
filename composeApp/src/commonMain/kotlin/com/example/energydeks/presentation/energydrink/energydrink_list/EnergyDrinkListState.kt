package com.example.energydeks.presentation.energydrink.energydrink_list

import com.example.energydeks.core.presentation.UiText
import com.example.energydeks.domain.energydrink.model.EnergyDrink

data class EnergyDrinkListState(
    val searchQuery: String = "",
    val searchResult:List<EnergyDrink> = emptyList(),
    val isLoading: Boolean = true,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)
