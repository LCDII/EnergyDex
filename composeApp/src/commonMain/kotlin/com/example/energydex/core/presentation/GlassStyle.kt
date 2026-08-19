package com.example.energydex.core.presentation

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

/**
 * Единый переключатель стиля стекла (таб + search + toggle + все кнопки):
 *  - true  = «тяжёлый» (vibrancy + blur(8) + lens(24,24)) — как рецепт LiquidBottomTabs
 *  - false = «лёгкий» (colorControls + blur(16)) — рецепт FrostedListHeader, плавнее на скролле
 */
val UseHeavyGlass = true

/** Пресет-обёртка над [glassBackdrop] для «контейнеров» (search, подложка таба, подложка toggle). */
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

/** Пресет-обёртка над [glassBackdrop] для «выбранной части» (капсула таба, thumb toggle, кнопки). */
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
    )
} else {
    glassBackdrop(
        backdrop = backdrop,
        shape = shape,
        effects = {
            colorControls(brightness = 0.1f, saturation = 1.5f)
            blur(radius = 16.dp.toPx())
        },
        highlight = { Highlight.Default },
        tintBrush = tintBrush,
        tintOpacity = tintOpacity,
    )
}