package com.example.energydeks.data.energydrink.repository

import com.example.EnergyDrinkTagQueries
import com.example.energydeks.core.domain.DataError
import com.example.energydeks.core.domain.EmptyResult
import com.example.energydeks.core.domain.Result
import com.example.energydeks.domain.energydrink.repository.EnergyDrinkTagRepository

class EnergyDrinkTagRepositoryImpl(
    private val energyDrinkTagQueries: EnergyDrinkTagQueries
): EnergyDrinkTagRepository {
    override suspend fun attachTagToEnergyDrink(
        tagId: Long,
        energyDrinkId: Long
    ): EmptyResult<DataError.Local> =
        try {
            energyDrinkTagQueries.insertEnergyDrinkTag(
                tagId = tagId,
                energyDrinkId = energyDrinkId
            )
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.DOESNT_EXISTS)//TODO something else
        }

    override suspend fun detachTagFromEnergyDrink(
        tagId: Long,
        energyDrinkId: Long): EmptyResult<DataError.Local> =
        try {
            energyDrinkTagQueries.deleteEnergyDrinkTag(
                tagId = tagId,
                energyDrinkId = energyDrinkId
            )
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.DOESNT_EXISTS)//TODO something else
        }
}