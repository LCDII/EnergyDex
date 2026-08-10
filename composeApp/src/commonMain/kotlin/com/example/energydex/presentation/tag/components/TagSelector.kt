package com.example.energydex.presentation.tag.components

import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.energydex.core.presentation.PrimaryOrange
import com.example.energydex.core.presentation.SecondaryOrange
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.domain.tag.model.Tag

@Composable
fun TagSelector(
    availableTags: List<Tag>,
    selectedTagIds: Set<Long>,
    onTagToggle: (Long) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        availableTags.forEach { tag ->
            val selected = tag.id in selectedTagIds
            FilterChip(
                selected = selected,
                onClick = { onTagToggle(tag.id) },
                label = { Text(tag.name) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = PrimaryOrange,
                    selectedLabelColor = AccentWhite,
                    labelColor = SecondaryOrange
                )
            )
        }
    }
}
