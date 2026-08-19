package com.example.energydex.core.presentation

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val GlassPanelTint = PrimaryPurple.copy(alpha = 0.45f)
val GlassCardTint = PrimaryPurple.copy(alpha = 0.55f)
val GlassChipTint = PrimaryPurple.copy(alpha = 0.40f)

val GlassEdgeHighlight = Brush.verticalGradient(
    listOf(
        Color.White.copy(alpha = 0.45f),
        Color.White.copy(alpha = 0.04f)
    )
)

val GlassActiveGradient = Brush.verticalGradient(
    listOf(PrimaryOrange, SecondaryOrange)
)

val GlassButtonGradient = Brush.verticalGradient(
    listOf(PrimaryOrange, SecondaryOrange)
)

private val TabDrinkGreen = Color(0xFF27CF51)
private val TabDrinkLight = Color(0xFF47FF75)
private val TabDrinkBright = Color(0xFF75F696)

val TabGradient = Brush.horizontalGradient(
    colorStops = arrayOf(
        0.20f to TabDrinkGreen,
        0.50f to TabDrinkLight,
        0.95f to TabDrinkBright,
    )
)