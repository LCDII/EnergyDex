package dev.lcdii.energydex.domain.energydrink.usecase

import dev.lcdii.energydex.core.domain.DataError
import dev.lcdii.energydex.core.domain.EmptyResult
import dev.lcdii.energydex.domain.energydrink.repository.EnergyDrinkTagRepository

class UpdateEnergyDrinkTagRelationsUseCase(
    private val repository: EnergyDrinkTagRepository
) {
    suspend operator fun invoke(
        tagId: Long,
        previousDrinkIds: Set<Long>,
        selectedDrinkIds: Set<Long>
    ): EmptyResult<DataError.Local> = repository.updateTagRelations(
        tagId = tagId,
        previousDrinkIds = previousDrinkIds,
        selectedDrinkIds = selectedDrinkIds
    )
}
