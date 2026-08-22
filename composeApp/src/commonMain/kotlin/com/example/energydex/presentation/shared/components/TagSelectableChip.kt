package com.example.energydex.presentation.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.example.energydex.domain.tag.model.Tag
import com.kashif_e.backdrop.Backdrop

@Composable
fun TagSelectableChip(
    tag: Tag,
    selected: Boolean,
    onClick: () -> Unit,
    backdrop: Backdrop,
    modifier: Modifier = Modifier
) {
    TagChip(
        tag = tag,
        backdrop = backdrop,
        modifier = modifier
            .alpha(if (selected) 1f else 0.5f)
            .clickable(onClick = onClick)
    )
}
