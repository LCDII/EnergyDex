package dev.lcdii.energydex.domain.energydrink.usecase

import dev.lcdii.energydex.domain.energydrink.repository.EnergyDrinkTagRepository

class ObserveEnergyDrinkTagRelationsUseCase(
    private val repository: EnergyDrinkTagRepository
) {
    operator fun invoke() = repository.observeAllRelations()
}
