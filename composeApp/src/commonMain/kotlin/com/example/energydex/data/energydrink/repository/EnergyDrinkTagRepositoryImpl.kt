package com.example.energydex.data.energydrink.repository

import com.example.EnergyDrinkTagQueries
import com.example.energydex.core.domain.DataError
import com.example.energydex.core.domain.EmptyResult
import com.example.energydex.presentation.energydrink.energydrink_section.components.EnergyDrinkListSortOptions
import com.example.energydex.core.domain.Result
import com.example.energydex.data.energydrink.mappers.toEnergyDrink
import com.example.energydex.data.tag.mappers.toTag
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.domain.energydrink.repository.EnergyDrinkTagRepository
import com.example.EnergyDrinkLocalMetadataQueries
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class EnergyDrinkTagRepositoryImpl(
    private val energyDrinkTagQueries: EnergyDrinkTagQueries,
    private val localMetadataQueries: EnergyDrinkLocalMetadataQueries
): EnergyDrinkTagRepository {
    override suspend fun attachTagToEnergyDrink(
        tagId: Long,
        energyDrinkId: Long
    ): EmptyResult<DataError.Local> =
        try {
            energyDrinkTagQueries.insertEnergyDrinkTag(
                tagId = tagId,
                energyDrinkId = energyDrinkId
            )
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.DOESNT_EXISTS)//TODO something else
        }

    override suspend fun attachTagsToEnergyDrinks(
        energyDrinkIds: Set<Long>,
        tagIds: Set<Long>
    ): EmptyResult<DataError.Local> = try {
        energyDrinkTagQueries.transaction {
            for (energyDrinkId in energyDrinkIds) {
                for (tagId in tagIds) {
                    energyDrinkTagQueries.insertEnergyDrinkTag(
                        tagId = tagId,
                        energyDrinkId = energyDrinkId
                    )
                }
            }
        }
        Result.Success(Unit)
    } catch (_: Exception) {
        Result.Error(DataError.Local.DOESNT_EXISTS)
    }

    override suspend fun detachTagFromEnergyDrink(
        tagId: Long,
        energyDrinkId: Long): EmptyResult<DataError.Local> =
        try {
            energyDrinkTagQueries.deleteEnergyDrinkTag(
                tagId = tagId,
                energyDrinkId = energyDrinkId
            )
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.DOESNT_EXISTS)//TODO something else
        }

    override suspend fun updateTagRelations(
        tagId: Long,
        previousDrinkIds: Set<Long>,
        selectedDrinkIds: Set<Long>
    ): EmptyResult<DataError.Local> = try {
        energyDrinkTagQueries.transaction {
            for (drinkId in previousDrinkIds - selectedDrinkIds) {
                energyDrinkTagQueries.deleteEnergyDrinkTag(
                    tagId = tagId,
                    energyDrinkId = drinkId
                )
            }
            for (drinkId in selectedDrinkIds - previousDrinkIds) {
                energyDrinkTagQueries.insertEnergyDrinkTag(
                    tagId = tagId,
                    energyDrinkId = drinkId
                )
            }
        }
        Result.Success(Unit)
    } catch (_: Exception) {
        Result.Error(DataError.Local.DOESNT_EXISTS)
    }

    override fun observeAllRelations(): Flow<Unit> =
        energyDrinkTagQueries.selectAllRelations()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { Unit }

    override fun observeEnergyDrinksForTag(
        tagId: Long,
        query: String,
        sortOption: EnergyDrinkListSortOptions
    ): Flow<List<EnergyDrink>> = combine(
        energyDrinkTagQueries
            .selectEnergyDrinksForTag(
                tagId = tagId,
                nameQuery = query,
                sortOption = sortOption.name
            )
            .asFlow()
            .mapToList(Dispatchers.Default),
        localMetadataQueries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { metadata -> metadata.associate { it.energyDrinkId to it.localImagePath } }
    ) { entities, imagePaths ->
        entities.map { entity ->
            val tags = withContext(Dispatchers.Default) {
                energyDrinkTagQueries
                    .selectTagsForEnergyDrink(entity.id)
                    .awaitAsList()
                    .map { it.toTag() }
            }
            entity.toEnergyDrink(tags, imagePaths[entity.id])
        }
    }
}
