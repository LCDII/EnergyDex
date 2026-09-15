package dev.lcdii.energydex.core.presentation

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val GlassPanelTint = AppBackground.copy(alpha = 0.45f)

val GlassEdgeHighlight = Brush.verticalGradient(
    listOf(
        Color.White.copy(alpha = 0.45f),
        Color.White.copy(alpha = 0.04f)
    )
)

val GreenGradientHorizontal = Brush.horizontalGradient(
    colorStops = arrayOf(
        0.20f to Color(0xFF27CF51),
        0.50f to Color(0xFF47FF75),
        0.95f to Color(0xFF75F696),
    )
)

val GreenGradientVertical = Brush.verticalGradient(
    colorStops = arrayOf(
        0.20f to Color(0xFF27CF51),
        0.50f to Color(0xFF47FF75),
        0.95f to Color(0xFF75F696),
    )
)

val AccentBlueGradient = Brush.horizontalGradient(
    colorStops = arrayOf(
        0.20f to Color(0xFF1A7FBB),   // сине-голубой
        0.50f to Color(0xFF3CA4FF),
        0.95f to Color(0xFF6AB9FE)
    )
)

val AccentOrangeGradient = Brush.horizontalGradient(
    colorStops = arrayOf(
        0.20f to TagOrangeColor,
        0.50f to Color(0xFFFFA351),
        0.95f to Color(0xFFFFB97C)
    )
)

val AccentIndigoGradient = Brush.horizontalGradient(
    colorStops = arrayOf(
        0.20f to TagPurpleColor,
        0.50f to Color(0xFF7F74FF),
        0.95f to Color(0xFF9A92FF)
    )
)

val AccentRedGradient = Brush.horizontalGradient(
    colorStops = arrayOf(
        0.20f to TagRedColor,
        0.50f to Color(0xFFFF585C),
        0.95f to Color(0xFFFF6E71)
    )
)

val AccentRedGradientVertical = Brush.verticalGradient(
    colorStops = arrayOf(
        0.20f to TagRedColor,
        0.50f to Color(0xFFFF585C),
        0.95f to Color(0xFFFF6E71)
    )
)



val AccentPinkGradient = Brush.horizontalGradient(
    colorStops = arrayOf(
        0.20f to Color(0xFFE94F9A),
        0.50f to Color(0xFFFF79BC),
        0.95f to Color(0xFFFFA0D0)
    )
)

val AccentYellowGradient = Brush.horizontalGradient(
    colorStops = arrayOf(
        0.20f to Color(0xFFE8AE24),
        0.50f to Color(0xFFFFD166),
        0.95f to Color(0xFFFFE39A)
    )
)

val AccentAmberGradient = Brush.horizontalGradient(
    colorStops = arrayOf(
        0.20f to Color(0xFFD4A017),
        0.50f to Color(0xFFFFCC33),
        0.95f to Color(0xFFFFE066)
    )
)


fun tagGradient(value: String): Brush = when (value) {
    TagBlueValue -> AccentBlueGradient
    TagPurpleValue -> AccentIndigoGradient
    TagRedValue -> AccentRedGradient
    TagYellowValue -> AccentYellowGradient
    TagPinkValue -> AccentPinkGradient
    else -> AccentOrangeGradient
}
