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
    listOf(
        PrimaryOrange.copy(alpha = 0.9f),
        SecondaryOrange.copy(alpha = 0.9f)
    )
)

val GlassTabBorder = Brush.horizontalGradient(
    listOf(PrimaryOrange, PrimaryOrangeGradientEnd)
)