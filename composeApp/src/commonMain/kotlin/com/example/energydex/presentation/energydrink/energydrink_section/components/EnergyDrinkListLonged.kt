package com.example.energydex.presentation.energydrink.energydrink_section.components

import androidx.compose.foundation.layout.Arrangement
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

@Composable
fun EnergyDrinkListLonged (
    energyDrinks: List<EnergyDrink>,
    onEnergyDrinkClick: (EnergyDrink) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
            modifier = modifier,
            state = scrollState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                items = energyDrinks,
                key = { it.id }
            ) { energyDrink ->
                EnergyDrinkItemLonged(
                    energyDrink = energyDrink,
                    onClick = {
                        onEnergyDrinkClick(energyDrink)
                    },
                    modifier = Modifier
                        .widthIn(max = 800.dp)
                        .fillMaxWidth()
                )
            }
    }
}
