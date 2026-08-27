package com.example.energydex.presentation.energydrink.energydrink_detail

import com.example.energydex.core.presentation.UiText
import com.example.energydex.domain.energydrink.model.EnergyDrink

data class EnergyDrinkDetailState(
    val isLoading: Boolean = true,
    val currentDrink: EnergyDrink? = null,
    val errorMessage: UiText? = null,
    //TODO
)
