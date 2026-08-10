package com.example.energydex.presentation.tag.tag_drink_selection

import com.example.energydex.core.presentation.UiText
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.presentation.shared.components.EnergyDrinkSectionTab

data class TagDrinkSelectionState(
    val isLoading: Boolean = true,
    val drinks: List<EnergyDrink> = emptyList(),
    val searchQuery: String = "",
    val selectedDrinkIds: Set<Long> = emptySet(),
    val initialDrinkIds: Set<Long> = emptySet(),
    val selectedTab: EnergyDrinkSectionTab = EnergyDrinkSectionTab.SQUARED,
    val isSaved: Boolean = false,
    val isSaving: Boolean = false,
    val errorMessage: UiText? = null
)
