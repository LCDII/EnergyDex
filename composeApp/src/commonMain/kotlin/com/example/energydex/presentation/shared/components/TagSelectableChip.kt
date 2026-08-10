package com.example.energydex.presentation.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.energydex.core.presentation.SecondaryOrange
import com.example.energydex.core.presentation.tagColor
import com.example.energydex.core.presentation.tagTextColor
import com.example.energydex.domain.tag.model.Tag

@Composable
fun TagSelectableChip(
    tag: Tag,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor = if (selected) tagColor(tag.color) else tagColor(tag.color).copy(alpha = 0.3f)
    val contentColor = if (selected) tagTextColor(containerColor) else SecondaryOrange
    TagChip(
        tag = tag,
        containerColor = containerColor,
        contentColor = contentColor,
        modifier = modifier.clickable(onClick = onClick)
    )
}