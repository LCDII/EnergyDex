package com.example.energydeks.presentation.energydrink.energydrink_section

import androidx.compose.runtime.Composable
import com.example.energydeks.domain.energydrink.model.EnergyDrink

@Composable
fun EnergyDrinkScreenRoot(
    viewModel: EnergyDrinkSectionViewModel,//TODO
    onEnergyDrinkClick: (EnergyDrink) -> Unit
) {
    //TODO state

    //TODO EnergyDrinkScreen
}

@Composable
fun EnergyDrinkScreen(
    state: EnergyDrinkSectionAction,
    onAction: (EnergyDrinkSectionAction) -> Unit
) {
    //TODO  Screen of energetics
}