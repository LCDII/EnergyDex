package com.example.energydeks.domain.energydrink.usecase

import com.example.energydeks.domain.energydrink.repository.EnergyDrinkRepository

class GetAllEnergyDrinksUseCase(
    private val energyDrinkRepository: EnergyDrinkRepository
) {
    suspend operator fun invoke() = energyDrinkRepository.getAllEnergyDrinks()
}