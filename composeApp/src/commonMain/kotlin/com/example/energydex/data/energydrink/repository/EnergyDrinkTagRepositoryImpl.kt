package com.example.energydex.data.energydrink.repository

import com.example.EnergyDrinkTagQueries
import com.example.energydex.core.domain.DataError
import com.example.energydex.core.domain.EmptyResult
import com.example.energydex.core.domain.EnergyDrinkListSortOptions
import com.example.energydex.core.domain.Result
import com.example.energydex.data.energydrink.mappers.toEnergyDrink
import com.example.energydex.data.tag.mappers.toTag
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.domain.energydrink.repository.EnergyDrinkTagRepository
import com.example.EnergyDrinkLocalMetadataQueries
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
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

    override fun observeAllRelations(): Flow<Unit> =
        energyDrinkTagQueries.selectAllRelations()
            .asFlow()
            .mapToList(Dispatchers.IO)
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
            .mapToList(Dispatchers.IO),
        localMetadataQueries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { metadata -> metadata.associate { it.energyDrinkId to it.localImagePath } }
    ) { entities, imagePaths ->
        entities.map { entity ->
            val tags = withContext(Dispatchers.IO) {
                energyDrinkTagQueries
                    .selectTagsForEnergyDrink(entity.id)
                    .executeAsList()
                    .map { it.toTag() }
            }
            entity.toEnergyDrink(tags, imagePaths[entity.id])
        }
    }
}
