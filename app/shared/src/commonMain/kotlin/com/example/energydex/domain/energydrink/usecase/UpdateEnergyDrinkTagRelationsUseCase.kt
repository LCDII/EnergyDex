package com.example.energydex.domain.energydrink.usecase

import com.example.energydex.core.domain.DataError
import com.example.energydex.core.domain.EmptyResult
import com.example.energydex.domain.energydrink.repository.EnergyDrinkTagRepository

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
