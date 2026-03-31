package com.example.energydeks.energydrink.domain

import com.example.energydeks.core.domain.DataError
import com.example.energydeks.core.domain.EmptyResult
import com.example.energydeks.core.domain.Result

interface EnergyDrinkRepository {
    suspend fun searchEnergyDrink(query: String): Result<List<EnergyDrink>, DataError.Local>

    suspend fun addEnergyDrink(energyDrink: EnergyDrink): EmptyResult<DataError.Local>
    suspend fun deleteEnergyDrink(id: Long)
}