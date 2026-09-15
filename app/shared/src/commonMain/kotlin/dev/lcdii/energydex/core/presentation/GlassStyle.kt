package dev.lcdii.energydex.core.presentation

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.kashif_e.backdrop.Backdrop
import com.kashif_e.backdrop.effects.blur
import com.kashif_e.backdrop.effects.colorControls
import com.kashif_e.backdrop.effects.lens
import com.kashif_e.backdrop.effects.vibrancy
import com.kashif_e.backdrop.highlight.Highlight


val UseHeavyGlass = true

fun Modifier.glassContainer(
    backdrop: Backdrop,
    shape: Shape,
    tint: Color
): Modifier = if (UseHeavyGlass) {
    glassBackdrop(
        backdrop = backdrop,
        shape = shape,
        effects = {
            vibrancy()
            blur(radius = 8.dp.toPx())
            lens(refractionHeight = 24.dp.toPx(), refractionAmount = 24.dp.toPx())
        },
        highlight = { Highlight.Ambient },
        onDrawSurface = { drawRect(tint) }
    )
} else {
    glassBackdrop(
        backdrop = backdrop,
        shape = shape,
        effects = {
            colorControls(brightness = 0.05f, saturation = 1.5f)
            blur(radius = 16.dp.toPx())
        },
        highlight = { Highlight.Plain },
        onDrawSurface = { drawRect(tint) }
    )
}
fun Modifier.glassThumb(
    backdrop: Backdrop,
    shape: Shape,
    tintBrush: Brush,
    tintOpacity: Float = 0.7f
): Modifier = if (UseHeavyGlass) {
    glassBackdrop(
        backdrop = backdrop,
        shape = shape,
        effects = {
            vibrancy()
            blur(radius = 8.dp.toPx())
            lens(refractionHeight = 24.dp.toPx(), refractionAmount = 24.dp.toPx())
        },
        highlight = { Highlight.Ambient },
        tintBrush = tintBrush,
        tintOpacity = tintOpacity,
        edgeWidth = 1.dp,
        edge = tintBrush,
    )
} else {
    glassBackdrop(
        backdrop = backdrop,
        shape = shape,
        effects = {
            colorControls(brightness = 0.08f, saturation = 1.7f)
            blur(radius = 8.dp.toPx())
        },
        highlight = { Highlight.Default },
        tintBrush = tintBrush,
        tintOpacity = tintOpacity,
        edgeWidth = 1.dp,
        edge = tintBrush,
    )
}

fun Modifier.glassContainerColored(
    backdrop: Backdrop,
    shape: Shape,
    tintBrush: Brush
): Modifier = glassThumb(
    backdrop = backdrop,
    shape = shape,
    tintBrush = tintBrush,
    tintOpacity = 0.7f
)

fun Modifier.glassTag(
    backdrop: Backdrop,
    shape: Shape,
    tintBrush: Brush,
    tintOpacity: Float = 0.7f
): Modifier =
    glassBackdrop(
        backdrop = backdrop,
        shape = shape,
        highlight = { Highlight.Ambient },
        tintBrush = tintBrush,
        tintOpacity = tintOpacity,
        edgeWidth = 1.dp,
        edge = tintBrush,
    )
