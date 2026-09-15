package dev.lcdii.energydex.domain.energydrink.repository

import dev.lcdii.energydex.core.domain.DataError
import dev.lcdii.energydex.core.domain.EmptyResult
import dev.lcdii.energydex.presentation.energydrink.energydrink_section.components.EnergyDrinkListSortOptions
import dev.lcdii.energydex.core.domain.Result
import dev.lcdii.energydex.domain.energydrink.model.EnergyDrink
import kotlinx.coroutines.flow.Flow

interface EnergyDrinkRepository {
    fun observeEnergyDrinks(
        query: String,
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
