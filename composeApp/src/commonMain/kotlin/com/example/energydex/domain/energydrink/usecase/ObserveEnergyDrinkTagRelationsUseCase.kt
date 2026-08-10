package com.example.energydex.domain.energydrink.usecase

import com.example.energydex.domain.energydrink.repository.EnergyDrinkTagRepository

class ObserveEnergyDrinkTagRelationsUseCase(
    private val repository: EnergyDrinkTagRepository
) {
    operator fun invoke() = repository.observeAllRelations()
}
