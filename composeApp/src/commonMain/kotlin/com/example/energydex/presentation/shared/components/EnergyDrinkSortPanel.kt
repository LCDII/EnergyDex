package com.example.energydex.presentation.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.GlassPanelTint
import com.example.energydex.core.presentation.TabGradient
import com.example.energydex.core.presentation.TextOnGradient
import com.example.energydex.core.presentation.glassContainer
import com.example.energydex.core.presentation.glassThumb
import com.example.energydex.presentation.energydrink.energydrink_section.components.EnergyDrinkListSortOptions
import com.kashif_e.backdrop.Backdrop

@Composable
fun SortDropdownPanel(
    backdrop: Backdrop,
    currentOption: EnergyDrinkListSortOptions,
    onOptionSelected: (EnergyDrinkListSortOptions) -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(28.dp)
    Column(
        modifier = modifier
            .widthIn(min = 200.dp, max = 240.dp)
            .clip(shape)
            .glassContainer(
                backdrop = backdrop,
                shape = shape,
                tint = GlassPanelTint
            )
    ) {
        listOf(
            EnergyDrinkListSortOptions.TITLE_ASC,
            EnergyDrinkListSortOptions.DATE_ASC,
            EnergyDrinkListSortOptions.RATING_ASC
        ).forEach { fieldOption ->
            val isCurrent = currentOption.fieldGroup() == fieldOption.fieldGroup()
            SortDropdownRow(
                backdrop = backdrop,
                label = fieldOption.label(),
                directionText = if (isCurrent) currentOption.directionLabel() else null,
                selected = isCurrent,
                onClick = { onOptionSelected(fieldOption) }
            )
        }
    }
}

@Composable
private fun SortDropdownRow(
    backdrop: Backdrop,
    label: String,
    directionText: String?,
    selected: Boolean,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(28.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(shape)
            .then(
                if (selected) {
                    Modifier.glassThumb(
                        backdrop = backdrop,
                        shape = shape,
                        tintBrush = TabGradient,
                        tintOpacity = 0.7f
                    )
                } else {
                    Modifier
                }
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                color = if (selected) TextOnGradient else AccentWhite,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                )
            )
            if (directionText != null) {
                Text(
                    text = directionText,
                    color = TextOnGradient,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}

private fun EnergyDrinkListSortOptions.fieldGroup(): String = name.substringBefore("_")

private fun EnergyDrinkListSortOptions.label(): String = when (this) {
    EnergyDrinkListSortOptions.TITLE_ASC,
    EnergyDrinkListSortOptions.TITLE_DESC -> "Title"
    EnergyDrinkListSortOptions.DATE_ASC,
    EnergyDrinkListSortOptions.DATE_DESC -> "Date"
    EnergyDrinkListSortOptions.RATING_ASC,
    EnergyDrinkListSortOptions.RATING_DESC -> "Rating"
}

private fun EnergyDrinkListSortOptions.directionLabel(): String = when (this) {
    EnergyDrinkListSortOptions.TITLE_ASC -> "A→Z"
    EnergyDrinkListSortOptions.TITLE_DESC -> "Z→A"
    EnergyDrinkListSortOptions.DATE_ASC -> "old"
    EnergyDrinkListSortOptions.DATE_DESC -> "new"
    EnergyDrinkListSortOptions.RATING_ASC -> "Worst"
    EnergyDrinkListSortOptions.RATING_DESC -> "Best"
}