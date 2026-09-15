package dev.lcdii.energydex.presentation.energydrink.energydrink_section.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.lcdii.energydex.core.presentation.AppBackground
import dev.lcdii.energydex.domain.energydrink.model.EnergyDrink
import dev.lcdii.energydex.presentation.shared.components.EnergyDrinkListSquared
import dev.lcdii.energydex.domain.tag.model.Tag
import com.kashif_e.backdrop.backdrops.rememberCanvasBackdrop
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
        onEnergyDrinkClick = {},
        cardBackdrop = rememberCanvasBackdrop { drawRect(AppBackground) }
    )
}
