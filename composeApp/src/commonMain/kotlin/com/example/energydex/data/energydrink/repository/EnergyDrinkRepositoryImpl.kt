package com.example.energydex.data.energydrink.repository


import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.EnergyDrinkEntityQueries
import com.example.EnergyDrinkTagQueries
import com.example.energydex.core.domain.DataError
import com.example.energydex.core.domain.EmptyResult
import com.example.energydex.core.domain.EnergyDrinkListSortOptions
import com.example.energydex.core.domain.Result
import com.example.energydex.data.energydrink.mappers.toEnergyDrink
import com.example.energydex.data.tag.mappers.toTag
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.domain.energydrink.repository.EnergyDrinkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.time.Clock

class EnergyDrinkRepositoryImpl(
    private val energyDrinkEntityQueries: EnergyDrinkEntityQueries,
    private val energyDrinkTagQueries: EnergyDrinkTagQueries
) : EnergyDrinkRepository{

    override suspend fun createEnergyDrink(
        name: String,
        amount: Int,
        description: String?,
        rating: Double?,
        imagePath: String?
    ): EmptyResult<DataError.Local> =
        try {
            energyDrinkEntityQueries.insertEnergyDrink(
                name = name,
                amount = amount.toLong(),
                description = description,
                rating = rating,
                createdAt = Clock.System.now().toEpochMilliseconds(),
                updatedAt = Clock.System.now().toEpochMilliseconds(),
                imagePath = imagePath
            )
            Result.Success(Unit)
        } catch(e: Exception) {
            Result.Error(DataError.Local.ALREADY_EXISTS)
            //TODO make variant for DISK_FULL
        }

    override suspend fun updateEnergyDrink(
        id: Long,
        name: String,
        amount: Int,
        description: String?,
        rating: Double?,
        imagePath: String?
    ): EmptyResult<DataError.Local> =
       try {
        energyDrinkEntityQueries.updateEnergyDrink(
            id = id,
            name = name,
            amount = amount.toLong(),
            description = description,
            rating = rating,
            updatedAt = Clock.System.now().toEpochMilliseconds(),
            imagePath = imagePath
        )
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.DOESNT_EXISTS)
        }

    override suspend fun deleteEnergyDrink(id: Long): EmptyResult<DataError.Local> =
        try {
            energyDrinkEntityQueries.deleteEnergyDrink(id = id)
            energyDrinkTagQueries.deleteAllTagsForEnergyDrink(energyDrinkId = id)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.DOESNT_EXISTS)
        }

    override suspend fun getEnergyDrinkById(id: Long): Result<EnergyDrink, DataError.Local> =
        try {
            //for energyDrinkEntity mapper we need tags from another table
            val energyDrink = energyDrinkEntityQueries.selectById(id = id).executeAsOne()
            val tags = energyDrinkTagQueries.selectTagsForEnergyDrink(energyDrinkId = id).executeAsList().map { it.toTag() }
            Result.Success(energyDrink.toEnergyDrink(tags))
        } catch (e: Exception) {
            Result.Error(DataError.Local.DOESNT_EXISTS)
        }


    override fun searchEnergyDrinks(
        query: String,
        sortOption: EnergyDrinkListSortOptions
    ): Flow<List<EnergyDrink>>{
        return energyDrinkEntityQueries.searchEnenergtDrinks(
            nameQuery = query,
            sortOption = sortOption.name
        ).asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities ->
                entities.map { entity ->
                    val tags = withContext(Dispatchers.IO) {
                        energyDrinkTagQueries.selectTagsForEnergyDrink(entity.id)
                            .executeAsList()
                            .map { it.toTag() }
                    }
                    entity.toEnergyDrink(tags)
                }
            }
    }

    override fun observeAllEnergyDrinks(
        sortOption: EnergyDrinkListSortOptions
    ): Flow<List<EnergyDrink>> {
        return energyDrinkEntityQueries.selectAll(sortOption.name)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities ->
                entities.map { entity ->
                    val tags = withContext(Dispatchers.IO) {
                        energyDrinkTagQueries.selectTagsForEnergyDrink(entity.id)
                            .executeAsList()
                            .map { it.toTag() }
                    }
                    entity.toEnergyDrink(tags)
                }
            }
    }

}