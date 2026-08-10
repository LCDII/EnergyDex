package com.example.energydex.domain.energydrink.usecase

import com.example.energydex.domain.energydrink.repository.EnergyDrinkRepository

class GetEnergyDrinksForTagUseCase(
    private val energyDrinkRepository: EnergyDrinkRepository
) {
    suspend operator fun invoke(tagId: Long) =
        energyDrinkRepository.getEnergyDrinksForTag(tagId)
}
