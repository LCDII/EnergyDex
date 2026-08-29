package com.example.energydex.presentation.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.energydex.core.presentation.TabGradientVertical
import com.example.energydex.core.presentation.TextOnGradient
import com.example.energydex.core.presentation.glassThumb
import com.kashif_e.backdrop.Backdrop
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun GlassCircleButton(
    onClick: () -> Unit,
    contentDescription: String,
    icon: DrawableResource,
    backdrop: Backdrop,
    modifier: Modifier = Modifier,
    size: Dp = 58.dp,
    tintBrush: Brush = TabGradientVertical
) {
    Box(
        modifier = modifier
            .size(size)
            .glassThumb(
                backdrop = backdrop,
                shape = CircleShape,
                tintBrush = tintBrush,
                tintOpacity = 0.7f
            ),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(icon),
                contentDescription = contentDescription,
                tint = TextOnGradient,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}
