package dev.lcdii.energydex.domain.energydrink.repository

import dev.lcdii.energydex.core.domain.DataError
import dev.lcdii.energydex.core.domain.EmptyResult
import dev.lcdii.energydex.core.domain.Result

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
