package com.example.energydex.domain.energydrink.usecase

import com.example.energydex.domain.energydrink.repository.EnergyDrinkRepository

class CreateEnergyDrinkUseCase(
    private val energyDrinkRepository: EnergyDrinkRepository
) {
    suspend operator fun invoke(
        name: String,
        amount: Int,
        description: String?,
        rating: Int,
        createdAt: Long,
        updatedAt: Long,
        imagePath: String?
    ) = energyDrinkRepository.createEnergyDrink(
        name = name,
        amount = amount,
        description = description,
        rating = rating,
        createdAt = createdAt,
        updatedAt = updatedAt,
        imagePath = imagePath
    )

}