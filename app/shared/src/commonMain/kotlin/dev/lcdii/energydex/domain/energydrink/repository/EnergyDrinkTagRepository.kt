package dev.lcdii.energydex.domain.energydrink.repository

import dev.lcdii.energydex.core.domain.DataError
import dev.lcdii.energydex.core.domain.EmptyResult
import dev.lcdii.energydex.presentation.energydrink.energydrink_section.components.EnergyDrinkListSortOptions
import dev.lcdii.energydex.domain.energydrink.model.EnergyDrink
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
