package com.example.energydeks.presentation.energydrink.energydrink_list

import androidx.compose.runtime.Composable
import com.example.energydeks.domain.energydrink.EnergyDrink

@Composable
fun EnergyDrinkScreenRoot(
    viewModel: EnergyDrinkListViewModel,//TODO
    onEnergyDrinkClick: (EnergyDrink) -> Unit
) {
    //TODO state

    //TODO EnergyDrinkScreen
}

@Composable
fun EnergyDrinkScreen(
    state: EnergyDrinkListState,
    onAction: (EnergyDrinkAction) -> Unit
) {
    //TODO  Screen of energetics
}