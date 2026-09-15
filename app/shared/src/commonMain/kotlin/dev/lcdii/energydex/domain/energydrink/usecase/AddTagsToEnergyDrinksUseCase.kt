package dev.lcdii.energydex.domain.energydrink.usecase

import dev.lcdii.energydex.core.domain.DataError
import dev.lcdii.energydex.core.domain.EmptyResult
import dev.lcdii.energydex.domain.energydrink.repository.EnergyDrinkTagRepository

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
