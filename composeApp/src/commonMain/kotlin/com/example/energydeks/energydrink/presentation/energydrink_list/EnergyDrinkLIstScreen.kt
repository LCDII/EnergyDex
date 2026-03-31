package com.example.energydeks.energydrink.presentation.energydrink_list

import androidx.compose.runtime.Composable
import com.example.energydeks.energydrink.domain.EnergyDrink

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