package com.example.energydex.presentation.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.kashif_e.backdrop.Backdrop

@Composable
fun EnergyDrinkListSquared (
    energyDrinks: List<EnergyDrink>,
    onEnergyDrinkClick: (EnergyDrink) -> Unit,
    onEnergyDrinkLongClick: (EnergyDrink) -> Unit = {},
    onEnergyDrinkSelectionClick: (EnergyDrink) -> Unit = {},
    isSelectionMode: Boolean = false,
    modifier: Modifier = Modifier,
    scrollState: LazyGridState = rememberLazyGridState(),
    selectedDrinkIds: Set<Long> = emptySet(),
    cardBackdrop: Backdrop,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        state = scrollState,
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = energyDrinks,
            key = { it.id }
        ) { energyDrink ->
            EnergyDrinkItemSquared(
                energyDrink = energyDrink,
                onClick = { onEnergyDrinkClick(energyDrink) },
                onLongClick = { onEnergyDrinkLongClick(energyDrink) },
                onSelectionClick = { onEnergyDrinkSelectionClick(energyDrink) },
                isSelectionMode = isSelectionMode,
                cardBackdrop = cardBackdrop,
                    modifier = Modifier
                        .widthIn(max = 600.dp)
                        .fillMaxWidth(),
                    isSelected = energyDrink.id in selectedDrinkIds
                )
        }
    }
}
