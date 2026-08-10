package com.example.energydex.domain.energydrink.usecase

import com.example.energydex.core.domain.DataError
import com.example.energydex.core.domain.EmptyResult
import com.example.energydex.core.domain.Result
import com.example.energydex.domain.energydrink.repository.EnergyDrinkTagRepository

class UpdateEnergyDrinkTagRelationsUseCase(
    private val repository: EnergyDrinkTagRepository
) {
    suspend operator fun invoke(
        tagId: Long,
        previousDrinkIds: Set<Long>,
        selectedDrinkIds: Set<Long>
    ): EmptyResult<DataError.Local> {
        for (drinkId in previousDrinkIds - selectedDrinkIds) {
            val result = repository.detachTagFromEnergyDrink(tagId, drinkId)
            if (result is Result.Error) return Result.Error(result.error)
        }
        for (drinkId in selectedDrinkIds - previousDrinkIds) {
            val result = repository.attachTagToEnergyDrink(tagId, drinkId)
            if (result is Result.Error) return Result.Error(result.error)
        }
        return Result.Success(Unit)
    }
}
