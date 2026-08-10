package com.example.energydex.presentation.energydrink.energydrink_section.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.presentation.shared.components.EnergyDrinkItemSquared
import com.example.energydex.domain.tag.model.Tag
import kotlin.time.Instant

@Preview
@Composable
fun PreviewEnergyDrinkItemSquared()
{
    EnergyDrinkItemSquared(
        energyDrink = EnergyDrink(
            id = 1,
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
        ),
        onClick = {}
    )
}