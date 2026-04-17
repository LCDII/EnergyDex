package com.example.energydex.data.energydrink.repository

import com.example.EnergyDrinkTagQueries
import com.example.energydex.core.domain.DataError
import com.example.energydex.core.domain.EmptyResult
import com.example.energydex.core.domain.Result
import com.example.energydex.domain.energydrink.repository.EnergyDrinkTagRepository

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