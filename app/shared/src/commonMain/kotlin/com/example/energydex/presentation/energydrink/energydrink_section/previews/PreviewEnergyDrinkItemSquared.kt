package com.example.energydex.presentation.energydrink.energydrink_section.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.energydex.core.presentation.AppBackground
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.presentation.shared.components.EnergyDrinkItemSquared
import com.example.energydex.domain.tag.model.Tag
import com.kashif_e.backdrop.backdrops.rememberCanvasBackdrop
import kotlin.time.Instant

private fun sampleDrink(
    imagePath: String? = null,
    tags: List<Tag> = emptyList()
) = EnergyDrink(
    id = 1,
    name = "Monster Energy White Ultra",
    amount = 1,
    description = null,
    rating = 10.0,
    createdAt = Instant.parse("2006-10-05T12:00:00Z"),
    updatedAt = Instant.parse("2006-10-05T12:00:00Z"),
    imagePath = imagePath,
    tags = tags
)

private val sampleTags = listOf(
    Tag(id = 1, name = "Good AF", color = "#FF7700"),
    Tag(id = 2, name = "Chuds Drink", color = "#38BDF8"),
    Tag(id = 3, name = "Worst", color = "#22C55E"),
    Tag(id = 4, name = "Fruity", color = "#A855F7"),
    Tag(id = 5, name = "Sugar Free", color = "#EF4444"),
    Tag(id = 6, name = "Energy", color = "#FACC15"),
    Tag(id = 7, name = "Long Name Tag That Wraps", color = "#EC4899")
)

@Preview
@Composable
fun PreviewEnergyDrinkItemSquaredNoPhotoNoTags() {
    EnergyDrinkItemSquared(
        energyDrink = sampleDrink(),
        onClick = {},
        cardBackdrop = rememberCanvasBackdrop { drawRect(AppBackground) }
    )
}

@Preview
@Composable
fun PreviewEnergyDrinkItemSquaredNoPhotoWithTags() {
    EnergyDrinkItemSquared(
        energyDrink = sampleDrink(tags = sampleTags),
        onClick = {},
        cardBackdrop = rememberCanvasBackdrop { drawRect(AppBackground) }
    )
}

@Preview
@Composable
fun PreviewEnergyDrinkItemSquaredWithPhotoNoTags() {
    EnergyDrinkItemSquared(
        energyDrink = sampleDrink(imagePath = "file:///tmp/preview.png"),
        onClick = {},
        cardBackdrop = rememberCanvasBackdrop { drawRect(AppBackground) }
    )
}

@Preview
@Composable
fun PreviewEnergyDrinkItemSquaredWithPhotoWithTags() {
    EnergyDrinkItemSquared(
        energyDrink = sampleDrink(imagePath = "file:///tmp/preview.png", tags = sampleTags),
        onClick = {},
        cardBackdrop = rememberCanvasBackdrop { drawRect(AppBackground) }
    )
}