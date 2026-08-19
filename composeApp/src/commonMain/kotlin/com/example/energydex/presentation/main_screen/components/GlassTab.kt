package com.example.energydex.presentation.main_screen.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.energydex.core.presentation.GlassPanelTint
import com.example.energydex.core.presentation.TabGradient
import com.example.energydex.core.presentation.glassBackdrop
import com.kashif_e.backdrop.Backdrop
import com.kashif_e.backdrop.effects.blur
import com.kashif_e.backdrop.effects.colorControls
import com.kashif_e.backdrop.effects.lens
import com.kashif_e.backdrop.effects.vibrancy
import com.kashif_e.backdrop.highlight.Highlight
import com.kashif_e.backdrop.shadow.InnerShadow

/**
 * Переключатель варианта стекла таба:
 *  - false = «лёгкое» (для таба поверх движущегося списка, рецепт FrostedListHeader)
 *  - true  = «полное», как в README LiquidBottomTabs (vibrancy + lens) — сочнее, но тяжелее на скролле
 */
val UseFullGlassTab = true

fun Modifier.glassTabPlaqueLean(backdrop: Backdrop): Modifier = glassBackdrop(
    backdrop = backdrop,
    shape = RoundedCornerShape(28.dp),
    effects = {
        colorControls(brightness = 0.05f, saturation = 1.5f)
        blur(radius = 16.dp.toPx())
    },
    highlight = { Highlight.Plain },
    onDrawSurface = { drawRect(GlassPanelTint) }
)

fun Modifier.glassTabPlaqueFull(backdrop: Backdrop): Modifier = glassBackdrop(
    backdrop = backdrop,
    shape = RoundedCornerShape(28.dp),
    effects = {
        vibrancy()
        blur(radius = 8.dp.toPx())
        lens(refractionHeight = 24.dp.toPx(), refractionAmount = 24.dp.toPx())
    },
    highlight = { Highlight.Ambient },
    onDrawSurface = { drawRect(GlassPanelTint.copy(alpha = 0.5f)) }
)

fun Modifier.glassTabCapsuleLean(backdrop: Backdrop): Modifier = glassBackdrop(
    backdrop = backdrop,
    shape = RoundedCornerShape(percent = 50),
    tintBrush = TabGradient,
    tintOpacity = 0.7f,
    blurRadius = 16.dp,
    brightness = 0.1f,
    contrast = 1.1f,
    saturation = 1.5f,
    highlight = { Highlight.Default },
    innerShadow = { InnerShadow(radius = 8.dp) }
)

fun Modifier.glassTabCapsuleFull(backdrop: Backdrop): Modifier = glassBackdrop(
    backdrop = backdrop,
    shape = RoundedCornerShape(percent = 50),
    tintBrush = TabGradient,
    tintOpacity = 0.7f,
    effects = {
        vibrancy()
        blur(radius = 8.dp.toPx())
        lens(refractionHeight = 24.dp.toPx(), refractionAmount = 24.dp.toPx())
    },
    highlight = { Highlight.Ambient },
    innerShadow = { InnerShadow(radius = 8.dp) }
)