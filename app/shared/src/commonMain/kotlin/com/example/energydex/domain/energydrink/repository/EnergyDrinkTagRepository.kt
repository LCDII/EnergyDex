package com.example.energydex.domain.energydrink.repository

import com.example.energydex.core.domain.DataError
import com.example.energydex.core.domain.EmptyResult
import com.example.energydex.presentation.energydrink.energydrink_section.components.EnergyDrinkListSortOptions
import com.example.energydex.domain.energydrink.model.EnergyDrink
import kotlinx.coroutines.flow.Flow

interface EnergyDrinkTagRepository {
    suspend fun attachTagToEnergyDrink(
        tagId: Long,
        energyDrinkId: Long
    ): EmptyResult<DataError.Local>
    suspend fun attachTagsToEnergyDrinks(
        energyDrinkIds: Set<Long>,
        tagIds: Set<Long>
    ): EmptyResult<DataError.Local>
    suspend fun detachTagFromEnergyDrink(
        tagId: Long,
        energyDrinkId: Long
    ): EmptyResult<DataError.Local>
    suspend fun updateTagRelations(
        tagId: Long,
        previousDrinkIds: Set<Long>,
        selectedDrinkIds: Set<Long>
    ): EmptyResult<DataError.Local>
    fun observeEnergyDrinksForTag(
        tagId: Long,
        query: String,
        sortOption: EnergyDrinkListSortOptions
    ): Flow<List<EnergyDrink>>
    fun observeAllRelations(): Flow<Unit>
}
