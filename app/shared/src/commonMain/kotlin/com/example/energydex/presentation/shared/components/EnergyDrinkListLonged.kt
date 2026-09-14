package com.example.energydex.presentation.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.kashif_e.backdrop.Backdrop

@Composable
fun EnergyDrinkListLonged (
    energyDrinks: List<EnergyDrink>,
    onEnergyDrinkClick: (EnergyDrink) -> Unit,
    onEnergyDrinkLongClick: (EnergyDrink) -> Unit = {},
    onEnergyDrinkSelectionClick: (EnergyDrink) -> Unit = {},
    isSelectionMode: Boolean = false,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState(),
    selectedDrinkIds: Set<Long> = emptySet(),
    cardBackdrop: Backdrop,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
            modifier = modifier,
            state = scrollState,
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                items = energyDrinks,
                key = { it.id }
            ) { energyDrink ->
                EnergyDrinkItemLonged(
                    energyDrink = energyDrink,
                    onClick = { onEnergyDrinkClick(energyDrink) },
                    onLongClick = { onEnergyDrinkLongClick(energyDrink) },
                    onSelectionClick = { onEnergyDrinkSelectionClick(energyDrink) },
                    isSelectionMode = isSelectionMode,
                    cardBackdrop = cardBackdrop,
                    modifier = Modifier
                        .widthIn(max = 800.dp)
                        .fillMaxWidth(),
                    isSelected = energyDrink.id in selectedDrinkIds
                )
            }
    }
}
