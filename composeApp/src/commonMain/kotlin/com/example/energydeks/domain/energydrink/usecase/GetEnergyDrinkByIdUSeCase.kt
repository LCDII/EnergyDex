package com.example.energydeks.domain.energydrink.usecase

import com.example.energydeks.domain.energydrink.repository.EnergyDrinkRepository

class GetEnergyDrinkByIdUSeCase(
    private val energyDrinkRepository: EnergyDrinkRepository
) {
    suspend operator fun invoke(
        id: Long
    ) = energyDrinkRepository.getEnergyDrinkById(
        id = id
    )
}