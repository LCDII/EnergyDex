package dev.lcdii.energydex.domain.energydrink.usecase

import dev.lcdii.energydex.domain.energydrink.repository.EnergyDrinkRepository

class GetEnergyDrinkByIdUseCase(
    private val energyDrinkRepository: EnergyDrinkRepository
) {
    suspend operator fun invoke(
        id: Long
    ) = energyDrinkRepository.getEnergyDrinkById(
        id = id
    )
}