package com.example.energydex.domain.energydrink.usecase

import com.example.energydex.domain.energydrink.repository.EnergyDrinkRepository

class GetEnergyDrinkByIdUseCase(
    private val energyDrinkRepository: EnergyDrinkRepository
) {
    suspend operator fun invoke(
        id: Long
    ) = energyDrinkRepository.getEnergyDrinkById(
        id = id
    )
}