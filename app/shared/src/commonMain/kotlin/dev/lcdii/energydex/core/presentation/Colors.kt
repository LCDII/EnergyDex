package dev.lcdii.energydex.core.presentation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val DarkBlue = Color(0xFF0B405E)
val TestColor = Color(0xFF7D7093)
val TestBackgroundColor = Color(0xFFFFBDBD)
val TestBackgroundSurfaceColor = Color(0xFFFF4B4B)


val PrimaryOrange = Color(0xFFFF7700)
val SecondaryOrange = Color(0xFFFFB347)
val PrimaryOrangeGradientEnd = Color(0xFFFF5500)

val AppBackground = Color(0xFF1E1E1E)
val SecondaryPurple = Color(0x73995EFF)

val TextOnGradient = AppBackground
val TextOnBackground = Color(0xFFFFFFFF)
val AccentWhite = TextOnBackground
val ErrorRed = Color(0xFFFF0000)

val RatingGreen = Color(0xFF47FF75)

fun ratingStarTint(rating: Double?): Color = when {
    rating == null || rating < 4.0 -> ErrorRed
    rating < 7.0 -> PrimaryOrange
    rating < 10.0 -> RatingGreen
    else -> RatingGreen
}

const val TagOrangeValue = "#FF7700"
const val TagPurpleValue = "#A855F7"
const val TagBlueValue = "#38BDF8"
const val TagRedValue = "#EF4444"
const val TagYellowValue = "#FACC15"
const val TagPinkValue = "#EC4899"

val TagOrangeColor = PrimaryOrange
val TagLightOrangeColor = SecondaryOrange
val TagPurpleColor = Color(0xFFA855F7)
val TagBlueColor = Color(0xFF38BDF8)
val TagAmberColor = Color(0xFFD4A017)
val TagRedColor = Color(0xFFEF4444)
val TagYellowColor = Color(0xFFE8AE24)
val TagPinkColor = Color(0xFFE94F9A)

val RatingStarGradient = Brush.linearGradient(
    colorStops = arrayOf(
        0.00f to Color(0xFF7B2FF7),  // фиолетовый
        0.35f to Color(0xFFF107A3),  // горяч. розовый
        0.60f to Color(0xFFFF7A00),  // оранжевый
        0.85f to Color(0xFFFFD54F),  // золото
        1.00f to Color(0xFFFFF1B8)   // светло-золотой
    ),
    start = Offset.Zero,
    end = Offset.Infinite
)

val TagColorValues = listOf(
    TagOrangeValue,
    TagPurpleValue,
    TagBlueValue,
    TagRedValue,
    TagYellowValue,
    TagPinkValue
)

val SelectedColor = Color(0xFF27CF51)

fun tagColor(value: String): Color = when (value) {
    TagOrangeValue -> TagOrangeColor
    TagPurpleValue -> TagPurpleColor
    TagBlueValue -> TagBlueColor
    TagRedValue -> TagRedColor
    TagYellowValue -> TagYellowColor
    TagPinkValue -> TagPinkColor
    else -> SecondaryOrange
}

fun tagTextColor(value: String): Color = tagTextColor(tagColor(value))

fun tagTextColor(background: Color): Color {
    val luminance = 0.2126 * background.red +
        0.7152 * background.green +
        0.0722 * background.blue
    return if (luminance > 0.5) Color.Black else Color.White
}
