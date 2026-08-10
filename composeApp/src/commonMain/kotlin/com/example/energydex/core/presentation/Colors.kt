package com.example.energydex.core.presentation

import androidx.compose.ui.graphics.Color

val DarkBlue = Color(0xFF0B405E)
val TestColor = Color(0xFF7D7093)
val TestBackgroundColor = Color(0xFFFFBDBD)
val TestBackgroundSurfaceColor = Color(0xFFFF4B4B)


val PrimaryOrange = Color(0xFFFF7700)
val SecondaryOrange = Color(0xFFFFB347)
val PrimaryOrangeGradientEnd = Color(0xFFFF5500)

val PrimaryPurple = Color(0xFF1F1038)
val SecondaryPurple = Color(0x73995EFF)

val AccentWhite = Color(0xFFFFFFFF)
val ErrorRed = Color(0xFFFF0000)

const val TagOrangeValue = "#FF7700"
const val TagLightOrangeValue = "#FFB347"
const val TagPurpleValue = "#A855F7"
const val TagBlueValue = "#38BDF8"
const val TagGreenValue = "#22C55E"
const val TagRedValue = "#EF4444"
const val TagYellowValue = "#FACC15"
const val TagPinkValue = "#EC4899"

val TagOrangeColor = PrimaryOrange
val TagLightOrangeColor = SecondaryOrange
val TagPurpleColor = Color(0xFFA855F7)
val TagBlueColor = Color(0xFF38BDF8)
val TagGreenColor = Color(0xFF22C55E)
val TagRedColor = Color(0xFFEF4444)
val TagYellowColor = Color(0xFFFACC15)
val TagPinkColor = Color(0xFFEC4899)

val TagColorValues = listOf(
    TagOrangeValue,
    TagLightOrangeValue,
    TagPurpleValue,
    TagBlueValue,
    TagGreenValue,
    TagRedValue,
    TagYellowValue,
    TagPinkValue
)

fun tagColor(value: String): Color = when (value) {
    TagOrangeValue -> TagOrangeColor
    TagLightOrangeValue -> TagLightOrangeColor
    TagPurpleValue -> TagPurpleColor
    TagBlueValue -> TagBlueColor
    TagGreenValue -> TagGreenColor
    TagRedValue -> TagRedColor
    TagYellowValue -> TagYellowColor
    TagPinkValue -> TagPinkColor
    else -> SecondaryOrange
}
