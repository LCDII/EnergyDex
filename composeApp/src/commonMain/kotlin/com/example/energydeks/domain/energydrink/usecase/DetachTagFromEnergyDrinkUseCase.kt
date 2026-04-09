package com.example.energydeks.domain.energydrink.usecase

import com.example.energydeks.domain.energydrink.repository.EnergyDrinkTagRepository

class DetachTagFromEnergyDrinkUseCase(
    private val energyDrinkTagRepository: EnergyDrinkTagRepository
) {
    suspend operator fun invoke(
        tagId: Long,
        energyDrinkId: Long
    ) = energyDrinkTagRepository.detachTagFromEnergyDrink(
        tagId = tagId,
        energyDrinkId = energyDrinkId
    )
}