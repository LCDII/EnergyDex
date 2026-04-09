package com.example.energydeks.presentation.energydrink.energydrink_list.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.energydeks.domain.energydrink.model.EnergyDrink
import com.example.energydeks.presentation.energydrink.energydrink_list.components.EnergyDrinkListLonged
import com.example.energydeks.domain.tag.model.Tag
import kotlin.time.Instant

@Preview
@Composable
fun PreviewEnergyDrinkListLonged() {

    EnergyDrinkListLonged(
        energyDrinks = List(100) { index ->
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
        },
        onEnergyDrinkClick = {}
    )
}