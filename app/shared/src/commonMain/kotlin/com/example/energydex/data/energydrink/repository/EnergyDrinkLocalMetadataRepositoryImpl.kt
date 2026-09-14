package com.example.energydex.data.energydrink.repository

import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import com.example.EnergyDrinkLocalMetadataQueries
import com.example.energydex.core.domain.DataError
import com.example.energydex.core.domain.EmptyResult
import com.example.energydex.core.domain.Result
import com.example.energydex.domain.energydrink.repository.EnergyDrinkLocalMetadataRepository

class EnergyDrinkLocalMetadataRepositoryImpl(
    private val queries: EnergyDrinkLocalMetadataQueries
) : EnergyDrinkLocalMetadataRepository {
    override suspend fun saveImagePath(
        energyDrinkId: Long,
        localImagePath: String
    ): EmptyResult<DataError.Local> = try {
        queries.upsert(
            energyDrinkId = energyDrinkId,
            localImagePath = localImagePath
        )
        Result.Success(Unit)
    } catch (_: Exception) {
        Result.Error(DataError.Local.UNKNOWN)
    }

    override suspend fun getImagePath(
        energyDrinkId: Long
    ): Result<String?, DataError.Local> = try {
        Result.Success(
            queries.selectByEnergyDrinkId(energyDrinkId)
                .awaitAsOneOrNull()
                ?.localImagePath
        )
    } catch (_: Exception) {
        Result.Error(DataError.Local.UNKNOWN)
    }

    override suspend fun deleteImagePath(
        energyDrinkId: Long
    ): EmptyResult<DataError.Local> = try {
        queries.deleteByEnergyDrinkId(energyDrinkId)
        Result.Success(Unit)
    } catch (_: Exception) {
        Result.Error(DataError.Local.UNKNOWN)
    }
}
