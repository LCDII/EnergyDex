package com.example.energydeks.energydrink.presentation.energydrink_list.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.energydeks.energydrink.domain.EnergyDrink
import com.example.energydeks.energydrink.presentation.energydrink_list.components.EnergyDrinkItemSquared
import kotlin.time.Instant

@Preview
@Composable
fun PreviewEnergyDrinkItemSquared()
{
    EnergyDrinkItemSquared(
        energyDrink = EnergyDrink(
            id = 1,
            name = "Monster Energy White",
            description=null,
            rating = 10.0,
            createdAt = Instant.parse("2006-10-05T12:00:00Z"),
            updatedAt = Instant.parse("2006-10-05T12:00:00Z"),
            imagePath = null,
            tags = listOf("Good AF", "Chuds Drink", "Fuck Foids")
        ),
        onClick = {}
    )
}