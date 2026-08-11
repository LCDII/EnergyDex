package com.example.energydex.domain.energydrink.repository

import com.example.energydex.core.domain.DataError
import com.example.energydex.core.domain.EmptyResult
import com.example.energydex.core.domain.EnergyDrinkListSortOptions
import com.example.energydex.domain.energydrink.model.EnergyDrink
import kotlinx.coroutines.flow.Flow

interface EnergyDrinkTagRepository {
    suspend fun attachTagToEnergyDrink(
        tagId: Long,
        energyDrinkId: Long
    ): EmptyResult<DataError.Local>
    suspend fun detachTagFromEnergyDrink(
        tagId: Long,
        energyDrinkId: Long
    ): EmptyResult<DataError.Local>
    fun observeEnergyDrinksForTag(
        tagId: Long,
        query: String,
        sortOption: EnergyDrinkListSortOptions
    ): Flow<List<EnergyDrink>>
    fun observeAllRelations(): Flow<Unit>
}
