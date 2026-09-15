package dev.lcdii.energydex.presentation.energydrink.energydrink_detail

import dev.lcdii.energydex.core.presentation.UiText
import dev.lcdii.energydex.domain.energydrink.model.EnergyDrink

data class EnergyDrinkDetailState(
    val isLoading: Boolean = true,
    val currentDrink: EnergyDrink? = null,
    val errorMessage: UiText? = null,
    //TODO
)
