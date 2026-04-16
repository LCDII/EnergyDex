package com.example.energydeks.domain.energydrink.repository

import com.example.energydeks.core.domain.DataError
import com.example.energydeks.core.domain.EmptyResult
import com.example.energydeks.core.domain.EnergyDrinkListSortOptions
import com.example.energydeks.core.domain.Result
import com.example.energydeks.domain.energydrink.model.EnergyDrink
import kotlinx.coroutines.flow.Flow

interface EnergyDrinkRepository {
    suspend fun searchEnergyDrink(
        query: String,
        sortOption: EnergyDrinkListSortOptions
    ): Result<List<EnergyDrink>, DataError.Local>
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
    suspend fun getAllEnergyDrinks(sortOption: EnergyDrinkListSortOptions): Result<List<EnergyDrink>, DataError.Local>
}