package dev.lcdii.energydex.presentation.energydrink.energydrink_section

import dev.lcdii.energydex.core.presentation.UiText
import dev.lcdii.energydex.domain.energydrink.model.EnergyDrink
import dev.lcdii.energydex.domain.tag.model.Tag
import dev.lcdii.energydex.presentation.energydrink.energydrink_section.components.EnergyDrinkListSortOptions
import dev.lcdii.energydex.presentation.shared.components.EnergyDrinkSectionTab

data class EnergyDrinkSectionState(
    val searchQuery: String = "",
    val searchResult: List<EnergyDrink> = emptyList(),
    val selectedEnergyDrinkIds: List<Long> = emptyList(),
    val isSortMenuVisible: Boolean = false,
    val sortOption: EnergyDrinkListSortOptions = EnergyDrinkListSortOptions.DATE_DESC,
    val isLoading: Boolean = true,
    val isSelectionMode: Boolean = false,
    val isDeleteDialogVisible: Boolean = false,
    val isTagDialogVisible: Boolean = false,
    val tags: List<Tag> = emptyList(),
    val selectedTagIds: Set<Long> = emptySet(),
    val isBulkOperationRunning: Boolean = false,
    val selectedTabIndex: EnergyDrinkSectionTab = EnergyDrinkSectionTab.SQUARED,
    val errorMessage: UiText? = null
)
