package com.example.energydex.presentation.tag.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.energydex.domain.tag.model.Tag
import com.example.energydex.presentation.shared.components.TagSelectableChip
import com.example.energydex.core.presentation.AppBackground
import com.kashif_e.backdrop.backdrops.rememberCanvasBackdrop

@Composable
fun TagSelector(
    availableTags: List<Tag>,
    selectedTagIds: Set<Long>,
    onTagToggle: (Long) -> Unit
) {
    val backdrop = rememberCanvasBackdrop { drawRect(AppBackground) }
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        availableTags.forEach { tag ->
            TagSelectableChip(
                tag = tag,
                selected = tag.id in selectedTagIds,
                onClick = { onTagToggle(tag.id) },
                backdrop = backdrop
            )
        }
    }
}
