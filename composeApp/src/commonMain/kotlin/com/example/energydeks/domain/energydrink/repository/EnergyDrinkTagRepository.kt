package com.example.energydeks.domain.energydrink.repository

import com.example.energydeks.core.domain.DataError
import com.example.energydeks.core.domain.EmptyResult

interface EnergyDrinkTagRepository {
    suspend fun attachTagToEnergyDrink(
        tagId: Long,
        energyDrinkId: Long
    ): EmptyResult<DataError.Local>
    suspend fun detachTagFromEnergyDrink(
        tagId: Long,
        energyDrinkId: Long
    ): EmptyResult<DataError.Local>
}