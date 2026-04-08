package com.example.energydeks.energydrink.presentation.energydrink_list.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.energydeks.energydrink.domain.EnergyDrink
import com.example.energydeks.energydrink.presentation.energydrink_list.components.EnergyDrinkListSquared
import com.example.energydeks.tag.domain.Tag
import kotlin.time.Instant

@Preview
@Composable
fun PreviewEnergyDrinkListSquared() {

    EnergyDrinkListSquared(
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