package dev.lcdii.energydex.core.presentation

import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kashif_e.backdrop.Backdrop
import com.kashif_e.backdrop.BackdropEffectScope
import com.kashif_e.backdrop.backdrops.LayerBackdrop
import com.kashif_e.backdrop.drawBackdrop
import com.kashif_e.backdrop.effects.blur
import com.kashif_e.backdrop.effects.colorControls
import com.kashif_e.backdrop.highlight.Highlight
import com.kashif_e.backdrop.shadow.InnerShadow
import com.kashif_e.backdrop.shadow.Shadow

fun Modifier.glassBackdrop(
    backdrop: Backdrop,
    shape: Shape,
    // STANDART PARAMETERS
    effects: (BackdropEffectScope.() -> Unit)? = null,
    highlight: (() -> Highlight)? = null,
    shadow: (() -> Shadow)? = null,
    innerShadow: (() -> InnerShadow)? = null,
    layerBlock: (GraphicsLayerScope.() -> Unit)? = null,
    exportedBackdrop: LayerBackdrop? = null,
    onDrawBehind: (DrawScope.() -> Unit)? = null,
    onDrawBackdrop: (DrawScope.(DrawScope.() -> Unit) -> Unit)? = null,
    onDrawSurface: (DrawScope.() -> Unit)? = null,
    onDrawFront: (DrawScope.() -> Unit)? = null,
    //SHORTCUTS
    tint: Color = Color.Transparent,
    tintBrush: Brush? = null,
    tintOpacity: Float = 0.75f,
    blurRadius: Dp = 18.dp,
    brightness: Float = 0f,
    contrast: Float = 1f,
    saturation: Float = 1.2f,
    edgeWidth: Dp = 1.dp,
    edge: Brush = GlassEdgeHighlight
): Modifier {
    val fill: Brush = tintBrush ?: Brush.linearGradient(listOf(tint, tint))

    val finalEffects: BackdropEffectScope.() -> Unit = effects ?: run {
        {
            colorControls(
                brightness = brightness,
                contrast = contrast,
                saturation = saturation
            )
            blur(radius = blurRadius.toPx())
        }
    }
    val finalHighlight: () -> Highlight = highlight ?: { Highlight.Ambient }
    val finalShadow: () -> Shadow? = shadow ?: { null }
    val finalInnerShadow: () -> InnerShadow? = innerShadow ?: { null }
    val finalLayerBlock: GraphicsLayerScope.() -> Unit = layerBlock ?: {}
    val finalOnDrawBehind: DrawScope.() -> Unit = onDrawBehind ?: {}
    val finalOnDrawBackdrop: DrawScope.(DrawScope.() -> Unit) -> Unit = onDrawBackdrop ?: { drawIt -> drawIt() }
    val finalOnDrawSurface: DrawScope.() -> Unit = onDrawSurface ?: run {
        {
            val outline = shape.createOutline(size, layoutDirection, this)
            val path = Path().apply {
                when (outline) {
                    is Outline.Rounded -> addRoundRect(outline.roundRect)
                    is Outline.Rectangle -> addRect(outline.rect)
                    is Outline.Generic -> addPath(outline.path)
                }
            }
            clipPath(path) {
                drawRect(brush = fill, blendMode = BlendMode.Hue)
                drawRect(brush = fill, alpha = tintOpacity)
            }
        }
    }
    val finalOnDrawFront: DrawScope.() -> Unit = onDrawFront ?: {}

    return drawBackdrop(
        backdrop = backdrop,
        shape = { shape },
        effects = finalEffects,
        highlight = finalHighlight,
        shadow = finalShadow,
        innerShadow = finalInnerShadow,
        layerBlock = finalLayerBlock,
        exportedBackdrop = exportedBackdrop,
        onDrawBehind = finalOnDrawBehind,
        onDrawBackdrop = finalOnDrawBackdrop,
        onDrawSurface = finalOnDrawSurface,
        onDrawFront = finalOnDrawFront,
    ).border(edgeWidth, edge, shape)
}