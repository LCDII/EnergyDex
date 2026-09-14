package com.example.energydex.presentation.tag.tag_detail

import com.example.energydex.core.presentation.UiText
import com.example.energydex.presentation.energydrink.energydrink_section.components.EnergyDrinkListSortOptions
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.domain.tag.model.Tag
import com.example.energydex.presentation.shared.components.EnergyDrinkSectionTab

data class TagDetailState(
    val isLoading: Boolean = true,
    val currentTag: Tag? = null,
    val energyDrinks: List<EnergyDrink> = emptyList(),
    val taggedEnergyDrinkIds: Set<Long> = emptySet(),
    val selectedEnergyDrinkIds: Set<Long> = emptySet(),
    val isSelectionMode: Boolean = false,
    val isBulkOperationRunning: Boolean = false,
    val searchQuery: String = "",
    val selectedTabIndex: EnergyDrinkSectionTab = EnergyDrinkSectionTab.SQUARED,
    val sortOption: EnergyDrinkListSortOptions = EnergyDrinkListSortOptions.DATE_DESC,
    val isSortMenuVisible: Boolean = false,
    val isDeleteDialogVisible: Boolean = false,
    val errorMessage: UiText? = null,
    val isDeleted: Boolean = false
)
