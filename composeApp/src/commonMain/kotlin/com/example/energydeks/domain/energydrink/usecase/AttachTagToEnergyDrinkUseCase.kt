package com.example.energydeks.domain.energydrink.usecase

import com.example.energydeks.domain.energydrink.repository.EnergyDrinkTagRepository

class AttachTagToEnergyDrinkUseCase(
    private val energyDrinkTagRepository: EnergyDrinkTagRepository
) {
    suspend operator fun invoke(
        tagId: Long,
        energyDrinkId: Long
    ) = energyDrinkTagRepository.attachTagToEnergyDrink(
        tagId = tagId,
        energyDrinkId = energyDrinkId
    )
}