package com.example.energydeks.domain.energydrink.usecase

import com.example.energydeks.core.domain.EnergyDrinkListSortOptions
import com.example.energydeks.domain.energydrink.repository.EnergyDrinkRepository

class GetAllEnergyDrinksUseCase(
    private val energyDrinkRepository: EnergyDrinkRepository
) {
    suspend operator fun invoke(sortOption: EnergyDrinkListSortOptions) = energyDrinkRepository.getAllEnergyDrinks(sortOption)
}