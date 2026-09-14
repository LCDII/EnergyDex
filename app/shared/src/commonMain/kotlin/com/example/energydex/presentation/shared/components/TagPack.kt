package com.example.energydex.presentation.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.domain.tag.model.Tag
import com.kashif_e.backdrop.Backdrop

private val ChipGap = 4.dp
private val ChipRowHeight = 20.dp
private val ChipFontSize = 12.sp
private val ChipHorizontalPadding = 10.dp
private val ChipVerticalPadding = 3.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagPack(
    tags: List<Tag>,
    modifier: Modifier = Modifier,
    backdrop: Backdrop
) {
    if (tags.isEmpty()) return

    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .height(ChipRowHeight * 2 + ChipGap),
        horizontalArrangement = Arrangement.spacedBy(ChipGap),
        verticalArrangement = Arrangement.spacedBy(ChipGap),
        maxLines = 2,
        overflow = FlowRowOverflow.expandIndicator {
            EllipsisChip()
        }
    ) {
        tags.forEach { tag ->
            TagChip(
                tag = tag,
                backdrop = backdrop,
                fontSize = ChipFontSize,
                horizontalPadding = ChipHorizontalPadding,
                verticalPadding = ChipVerticalPadding
            )
        }
    }
}

@Composable
private fun EllipsisChip() {
    Text(
        text = "…",
        color = AccentWhite,
        fontSize = ChipFontSize,
        lineHeight = ChipFontSize,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(Color.White.copy(alpha = 0.15f))
    )
}
