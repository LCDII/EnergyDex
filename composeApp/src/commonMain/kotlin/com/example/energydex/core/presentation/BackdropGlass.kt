package com.example.energydex.core.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kashif_e.backdrop.Backdrop
import com.kashif_e.backdrop.drawBackdrop
import com.kashif_e.backdrop.effects.blur
import com.kashif_e.backdrop.effects.colorControls
import com.kashif_e.backdrop.highlight.Highlight
import com.kashif_e.backdrop.shadow.InnerShadow

fun Modifier.backdropGlass(
    backdrop: Backdrop,
    shape: Shape,
    tint: Color,
    blurRadius: Dp = 18.dp,
    saturation: Float = 1.2f,
    highlight: Highlight = Highlight.Ambient,
    innerShadow: InnerShadow? = null,
    edgeWidth: Dp = 1.dp,
    edge: Brush = GlassEdgeHighlight
): Modifier = drawBackdrop(
    backdrop = backdrop,
    shape = { shape },
    effects = {
        colorControls(saturation = saturation)
        blur(radius = blurRadius.toPx())
    },
    highlight = { highlight },
    innerShadow = { innerShadow },
)
    .background(tint, shape)
    .border(edgeWidth, edge, shape)