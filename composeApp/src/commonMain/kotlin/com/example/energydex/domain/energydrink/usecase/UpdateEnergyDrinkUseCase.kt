package com.example.energydex.domain.energydrink.usecase

import com.example.energydex.domain.energydrink.repository.EnergyDrinkRepository

class UpdateEnergyDrinkUseCase(
    private val energyDrinkRepository: EnergyDrinkRepository
) {
    suspend operator fun invoke(
        id: Long,
        name: String,
        amount: Int,
        description: String?,
        rating: Double?,
        updatedAt: Long,
        imagePath: String?
    ) = energyDrinkRepository.updateEnergyDrink(
        id = id,
        name = name,
        amount = amount,
        description = description,
        rating = rating,
        imagePath = imagePath
    )
}