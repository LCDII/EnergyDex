package com.example.energydex.domain.energydrink.usecase

import com.example.energydex.core.domain.EnergyDrinkListSortOptions
import com.example.energydex.domain.energydrink.repository.EnergyDrinkRepository

class GetAllEnergyDrinksUseCase(
    private val energyDrinkRepository: EnergyDrinkRepository
) {
    operator fun invoke(sortOption: EnergyDrinkListSortOptions) = energyDrinkRepository.observeAllEnergyDrinks(sortOption)
}