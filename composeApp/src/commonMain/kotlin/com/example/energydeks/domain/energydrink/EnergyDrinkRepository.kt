package com.example.energydeks.domain.energydrink

import com.example.energydeks.core.domain.DataError
import com.example.energydeks.core.domain.EmptyResult
import com.example.energydeks.core.domain.Result

interface EnergyDrinkRepository {
    suspend fun searchEnergyDrink(query: String): Result<List<EnergyDrink>, DataError.Local>
    suspend fun createEnergyDrink(
        name: String,
        amount: Int,
        description: String?,
        rating: Int,
        createdAt: Long,
        updatedAt: Long,
        imagePath: String?
    ): EmptyResult<DataError.Local>
    suspend fun updateEnergyDrink(
        id: Long,
        name: String,
        amount: Int,
        description: String?,
        rating: Int,
        updatedAt: Long,
        imagePath: String?
    ) : EmptyResult<DataError.Local>
    suspend fun deleteEnergyDrink(id: Long): EmptyResult<DataError.Local>
    suspend fun getEnergyDrinkById(id: Long): Result<EnergyDrink, DataError.Local>
    suspend fun getAllEnergyDrinks(): Result<List<EnergyDrink>, DataError.Local>
}