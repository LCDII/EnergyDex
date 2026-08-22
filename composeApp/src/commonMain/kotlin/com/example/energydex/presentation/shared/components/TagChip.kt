package com.example.energydex.presentation.shared.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.energydex.core.presentation.tagColor
import com.example.energydex.domain.tag.model.Tag

@Composable
fun TagChip(
    tag: Tag,
    modifier: Modifier = Modifier,
    containerColor: Color = tagColor(tag.color),
    contentColor: Color = Color.White,
    fontSize: TextUnit = 14.sp,
    horizontalPadding: Dp = 5.dp,
    verticalPadding: Dp = 2.dp
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(containerColor)
            .padding(horizontal = horizontalPadding, vertical = verticalPadding),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = tag.name,
            color = Color.White,
            fontSize = fontSize,
            lineHeight = fontSize,
            style = TextStyle(
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun TagChipPreview() {
    Column {
        Row {
            TagChip(
                tag = Tag(1, "red", "Energy"),
                containerColor = Color(0xFFE57373),
                contentColor = Color.White
            )
            TagChip(
                tag = Tag(2, "blue", "Zero Sugar"),
                containerColor = Color(0xFF64B5F6),
                contentColor = Color.White
            )
        }
        TagChip(
            tag = Tag(3, "green", "Longer tag example"),
            containerColor = Color(0xFF81C784),
            contentColor = Color.Black
        )
    }
}
