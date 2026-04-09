package com.example.energydeks.domain.energydrink.usecase

import com.example.energydeks.domain.energydrink.repository.EnergyDrinkRepository

class DeleteEnergyDrinkUseCase(
    private val energyDrinkRepository: EnergyDrinkRepository
) {
    suspend operator fun invoke(
        id: Long
    ) = energyDrinkRepository.deleteEnergyDrink(
        id = id
    )
}