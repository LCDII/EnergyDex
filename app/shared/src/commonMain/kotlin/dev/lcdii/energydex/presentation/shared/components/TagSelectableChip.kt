package dev.lcdii.energydex.presentation.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.lcdii.energydex.domain.tag.model.Tag
import com.kashif_e.backdrop.Backdrop

@Composable
fun TagSelectableChip(
    tag: Tag,
    selected: Boolean,
    onClick: () -> Unit,
    backdrop: Backdrop,
    fontSize: TextUnit = 14.sp,
    horizontalPadding: Dp = 5.dp,
    verticalPadding: Dp = 1.dp,
    modifier: Modifier = Modifier
) {
    TagChip(
            tag = tag,
            backdrop = backdrop,
            fontSize = fontSize,
            horizontalPadding = horizontalPadding,
            verticalPadding = verticalPadding,
            modifier = modifier
            .alpha(if (selected) 1f else 0.5f)
            .clickable(onClick = onClick)
    )
}
