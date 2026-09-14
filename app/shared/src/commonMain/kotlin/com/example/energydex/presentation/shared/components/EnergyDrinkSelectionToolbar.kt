package com.example.energydex.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.AppBackground
import com.example.energydex.core.presentation.PrimaryOrange

@Composable
fun EnergyDrinkSelectionToolbar(
    selectedCount: Int,
    isBusy: Boolean,
    onDeleteClick: () -> Unit,
    onTagClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(AppBackground)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextButton(onClick = onCancelClick, enabled = !isBusy) {
            Text("Cancel", color = AccentWhite)
        }
        Text(
            text = "$selectedCount selected",
            color = AccentWhite,
            modifier = Modifier.weight(1f)
        )
        TextButton(onClick = onTagClick, enabled = !isBusy) {
            Text("Tag", color = AccentWhite)
        }
        TextButton(onClick = onDeleteClick, enabled = !isBusy) {
            Text("Delete", color = PrimaryOrange)
        }
    }
}