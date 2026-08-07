package com.example.energydex.domain.energydrink.repository

import com.example.energydex.core.domain.DataError
import com.example.energydex.core.domain.EmptyResult
import com.example.energydex.core.domain.EnergyDrinkListSortOptions
import com.example.energydex.core.domain.Result
import com.example.energydex.domain.energydrink.model.EnergyDrink
import kotlinx.coroutines.flow.Flow

interface EnergyDrinkRepository {
    fun searchEnergyDrinks(
        query: String,
        sortOption: EnergyDrinkListSortOptions
    ): Flow<List<EnergyDrink>>
    fun observeAllEnergyDrinks(
        sortOption: EnergyDrinkListSortOptions
    ): Flow<List<EnergyDrink>>
    suspend fun createEnergyDrink(
        name: String,
        amount: Int,
        description: String?,
        rating: Double?,
    ): Result<Long, DataError.Local>
    suspend fun updateEnergyDrink(
        id: Long,
        name: String,
        amount: Int,
        description: String?,
        rating: Double?,
    ) : EmptyResult<DataError.Local>
    suspend fun deleteEnergyDrink(id: Long): EmptyResult<DataError.Local>
    suspend fun getEnergyDrinkById(id: Long): Result<EnergyDrink, DataError.Local>
}
