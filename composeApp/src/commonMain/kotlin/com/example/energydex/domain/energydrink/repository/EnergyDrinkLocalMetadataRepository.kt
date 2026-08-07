package com.example.energydex.domain.energydrink.repository

import com.example.energydex.core.domain.DataError
import com.example.energydex.core.domain.EmptyResult
import com.example.energydex.core.domain.Result

interface EnergyDrinkLocalMetadataRepository {
    suspend fun saveImagePath(
        energyDrinkId: Long,
        localImagePath: String
    ): EmptyResult<DataError.Local>

    suspend fun getImagePath(
        energyDrinkId: Long
    ): Result<String?, DataError.Local>

    suspend fun deleteImagePath(
        energyDrinkId: Long
    ): EmptyResult<DataError.Local>
}
