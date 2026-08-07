package com.example.energydex.presentation.energydrink.energydrink_update

import com.example.energydex.core.presentation.UiText
import com.example.energydex.presentation.energydrink.energydrink_add.EnergyDrinkAddState

data class EnergyDrinkUpdateState(
    val isLoading: Boolean = true,
    val form: EnergyDrinkAddState = EnergyDrinkAddState(),
    val errorMessage: UiText? = null
)
