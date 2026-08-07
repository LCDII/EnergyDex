package com.example.energydex.domain.energydrink.repository

import com.example.energydex.core.domain.DataError
import com.example.energydex.core.domain.EmptyResult

interface EnergyDrinkLocalMetadataRepository {
    suspend fun saveImagePath(
        energyDrinkId: Long,
        localImagePath: String
    ): EmptyResult<DataError.Local>
}
