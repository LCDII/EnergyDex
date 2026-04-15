package com.example.energydeks.presentation.energydrink.energydrink_detail

import com.example.energydeks.core.presentation.UiText
import com.example.energydeks.domain.energydrink.model.EnergyDrink

data class EnergyDrinkDetailState(
    val isLoading: Boolean = true,
    val showDeleteConfirmation: Boolean = false,
    val currentDrink: EnergyDrink? = null,
    val errorMessage: UiText? = null,
    //TODO
)
