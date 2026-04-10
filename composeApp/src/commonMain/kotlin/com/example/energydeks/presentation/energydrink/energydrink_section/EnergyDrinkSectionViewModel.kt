package com.example.energydeks.presentation.energydrink.energydrink_section

import androidx.lifecycle.ViewModel
import com.example.energydeks.domain.energydrink.usecase.CreateEnergyDrinkUseCase
import com.example.energydeks.domain.energydrink.usecase.DeleteEnergyDrinkUseCase
import com.example.energydeks.domain.energydrink.usecase.GetAllEnergyDrinksUseCase
import com.example.energydeks.domain.energydrink.usecase.UpdateEnergyDrinkUseCase

class EnergyDrinkSectionViewModel (
    private val createEnergyDrink: CreateEnergyDrinkUseCase,
    private val deleteEnergyDrink: DeleteEnergyDrinkUseCase,
    private val editEnergyDrin: UpdateEnergyDrinkUseCase,
    private val getAllEnergyDrinks: GetAllEnergyDrinksUseCase
): ViewModel() {

}