package dev.lcdii.energydex.presentation.tag.tag_detail

import dev.lcdii.energydex.core.presentation.UiText
import dev.lcdii.energydex.presentation.energydrink.energydrink_section.components.EnergyDrinkListSortOptions
import dev.lcdii.energydex.domain.energydrink.model.EnergyDrink
import dev.lcdii.energydex.domain.tag.model.Tag
import dev.lcdii.energydex.presentation.shared.components.EnergyDrinkSectionTab

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
