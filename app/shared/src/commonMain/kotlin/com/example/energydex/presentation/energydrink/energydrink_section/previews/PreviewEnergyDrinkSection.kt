package com.example.energydex.presentation.energydrink.energydrink_section.previews

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.domain.tag.model.Tag
import com.example.energydex.presentation.energydrink.energydrink_section.EnergyDrinkSection
import com.example.energydex.presentation.energydrink.energydrink_section.EnergyDrinkSectionState
import kotlin.time.Instant

@Preview
@Composable
fun TmpEnergyDrinkSectionPreview() {
    //Calling the stateless EnergyDrinkSection instead of EnergyDrinkSectionRoot
    // to avoid the "KoinApplication has not been started" error in the preview.
    MaterialTheme {
        EnergyDrinkSection(
            state = EnergyDrinkSectionState(
                isLoading = false,
                searchResult = List(100) { index ->
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
                }
            ),
            onAction = {}
        )
    }
}