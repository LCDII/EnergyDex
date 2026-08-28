package com.example.energydex.presentation.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.GlassPanelTint
import com.example.energydex.core.presentation.glassContainer
import com.kashif_e.backdrop.Backdrop
import energydex.composeapp.generated.resources.Res
import energydex.composeapp.generated.resources.ic_arrow_back
import org.jetbrains.compose.resources.painterResource

@Composable
fun GlassBackButton(
    backdrop: Backdrop,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(50)

    Box(
        modifier = modifier
            .width(72.dp)
            .height(50.dp)
            .clip(shape)
            .glassContainer(
                backdrop = backdrop,
                shape = shape,
                tint = GlassPanelTint
            ),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(Res.drawable.ic_arrow_back),
                contentDescription = "Back",
                tint = AccentWhite
            )
        }
    }
}
