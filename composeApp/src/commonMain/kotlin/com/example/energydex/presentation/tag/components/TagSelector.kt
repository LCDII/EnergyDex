package com.example.energydex.presentation.tag.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.energydex.domain.tag.model.Tag
import com.example.energydex.presentation.shared.components.TagSelectableChip
import com.example.energydex.core.presentation.AppBackground
import com.kashif_e.backdrop.backdrops.rememberCanvasBackdrop

@Composable
fun TagSelector(
    availableTags: List<Tag>,
    selectedTagIds: Set<Long>,
    onTagToggle: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val backdrop = rememberCanvasBackdrop { drawRect(AppBackground) }
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        availableTags.forEach { tag ->
            TagSelectableChip(
                tag = tag,
                selected = tag.id in selectedTagIds,
                onClick = { onTagToggle(tag.id) },
                backdrop = backdrop,
                fontSize = 16.sp,
                horizontalPadding = 12.dp,
                verticalPadding = 3.dp,
                modifier = Modifier
                    .widthIn(min = 64.dp)
                    .height(50.dp)
            )
        }
    }
}
