package com.example.energydeks.energydrink.presentation.energydrink_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.energydeks.energydrink.domain.EnergyDrink

@Composable
fun EnergyDrinkList (
    energyDrinks: List<EnergyDrink>,
    energyDrinksListViewTabIndex: Int,
    OnEnergyDrinkClick: (EnergyDrink) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = energyDrinks,
            key = { it.id }
        ) { energyDrink ->
            when(energyDrinksListViewTabIndex){
                0->EnergyDrinkItemSquared(
                    //TODO
                )
                1->EnergyDrinkItemLonged(
                    //TODO
                )
            }

        }
    }
}