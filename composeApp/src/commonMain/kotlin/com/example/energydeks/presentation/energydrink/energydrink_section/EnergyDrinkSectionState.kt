package com.example.energydeks.presentation.energydrink.energydrink_section

import com.example.energydeks.core.presentation.UiText
import com.example.energydeks.domain.energydrink.model.EnergyDrink
import com.example.energydeks.domain.tag.model.Tag
import com.example.energydeks.core.domain.EnergyDrinkListSortOptions
import com.example.energydeks.presentation.energydrink.energydrink_section.components.EnergyDrinkSectionTab
import kotlin.time.Instant

data class EnergyDrinkSectionState(
    val searchQuery: String = "",
    val searchResult: List<EnergyDrink> = List(100) { index ->
        EnergyDrink(
            id = index.toLong(),
            name = "Monster Energy White",
            amount = 1,
            description=null,
            rating = 10.0,
            createdAt = Instant.parse("2006-10-05T12:00:00Z"),
            updatedAt = Instant.parse("2006-10-05T12:00:00Z"),
            imagePath = null,
            tags = listOf(
                Tag(
                    id = 0,
                    name = "Good AF",
                    color = "#000000"
                ),
                Tag(
                    id = 0,
                    name = "Chuds Drink",
                    color = "#000000"
                )
            )
        )
    },//emptyList(),
    val selectedEnergyDrinkIds: List<Long> = emptyList(),
    val isSortMode: Boolean = false,
    val sortOption: EnergyDrinkListSortOptions = EnergyDrinkListSortOptions.RATING_DESC,
    val isLoading: Boolean = false,//true
    val isSelectionMode: Boolean = false,
    val selectedTabIndex: EnergyDrinkSectionTab = EnergyDrinkSectionTab.SQUARED,
    val errorMessage: UiText? = null
)
