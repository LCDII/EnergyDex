package com.example.energydex.presentation.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.energydex.core.presentation.tagGradient
import com.example.energydex.core.presentation.glassTag
import com.example.energydex.core.presentation.AppBackground
import com.example.energydex.domain.tag.model.Tag
import com.kashif_e.backdrop.Backdrop
import com.kashif_e.backdrop.backdrops.rememberCanvasBackdrop

@Composable
fun TagChip(
    tag: Tag,
    modifier: Modifier = Modifier,
    backdrop: Backdrop,
    fontSize: TextUnit = 14.sp,
    horizontalPadding: Dp = 5.dp,
    verticalPadding: Dp = 1.dp
) {
    val shape = RoundedCornerShape(50)
    val tagBrush = tagGradient(tag.color)
    Box(
        modifier = modifier
            .clip(shape)
            .glassTag(
                backdrop = backdrop,
                shape = shape,
                tintBrush = tagBrush,
                tintOpacity = 0.85f
            )
            .padding(horizontal = horizontalPadding, vertical = verticalPadding),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = tag.name,
            color = Color.White,
            fontSize = fontSize,
            lineHeight = fontSize,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun TagChipPreview() {
    val backdrop = rememberCanvasBackdrop { drawRect(AppBackground) }
    Column {
        Row {
            TagChip(
                tag = Tag(1, "red", "Energy"),
                backdrop = backdrop
            )
            TagChip(
                tag = Tag(2, "blue", "Zero Sugar"),
                backdrop = backdrop
            )
        }
        TagChip(
            tag = Tag(3, "green", "Longer tag example"),
            backdrop = backdrop
        )
    }
}
