package com.example.energydex.data.energydrink.repository

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
}
