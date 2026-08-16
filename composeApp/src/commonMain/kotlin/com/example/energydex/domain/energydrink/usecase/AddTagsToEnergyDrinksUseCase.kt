package com.example.energydex.domain.energydrink.usecase

import com.example.energydex.core.domain.DataError
import com.example.energydex.core.domain.EmptyResult
import com.example.energydex.domain.energydrink.repository.EnergyDrinkTagRepository

class AddTagsToEnergyDrinksUseCase(
    private val repository: EnergyDrinkTagRepository
) {
    suspend operator fun invoke(
        energyDrinkIds: Set<Long>,
        tagIds: Set<Long>
    ): EmptyResult<DataError.Local> = repository.attachTagsToEnergyDrinks(
        energyDrinkIds = energyDrinkIds,
        tagIds = tagIds
    )
}
