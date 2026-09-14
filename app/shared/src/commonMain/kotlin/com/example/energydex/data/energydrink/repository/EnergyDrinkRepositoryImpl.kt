package com.example.energydex.data.energydrink.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOne
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import app.cash.sqldelight.coroutines.mapToList
import com.example.EnergyDrinkEntityQueries
import com.example.EnergyDrinkLocalMetadataQueries
import com.example.EnergyDrinkTagQueries
import com.example.energydex.core.domain.DataError
import com.example.energydex.core.domain.EmptyResult
import com.example.energydex.presentation.energydrink.energydrink_section.components.EnergyDrinkListSortOptions
import com.example.energydex.core.domain.Result
import com.example.energydex.data.energydrink.mappers.toEnergyDrink
import com.example.energydex.data.tag.mappers.toTag
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.domain.energydrink.repository.EnergyDrinkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.time.Clock

class EnergyDrinkRepositoryImpl(
    private val energyDrinkEntityQueries: EnergyDrinkEntityQueries,
    private val energyDrinkTagQueries: EnergyDrinkTagQueries,
    private val localMetadataQueries: EnergyDrinkLocalMetadataQueries
) : EnergyDrinkRepository {
    override suspend fun createEnergyDrink(
        name: String,
        amount: Int,
        description: String?,
        rating: Double?
    ): Result<Long, DataError.Local> = withContext(Dispatchers.Default) {
        try {
            energyDrinkEntityQueries.insertEnergyDrink(
                name = name,
                amount = amount.toLong(),
                description = description,
                rating = rating,
                createdAt = Clock.System.now().toEpochMilliseconds(),
                updatedAt = Clock.System.now().toEpochMilliseconds()
            )
            Result.Success(energyDrinkEntityQueries.lastInsertRowId().awaitAsOne())
        } catch (_: Exception) {
            Result.Error(DataError.Local.ALREADY_EXISTS)
        }
    }

    override suspend fun updateEnergyDrink(
        id: Long,
        name: String,
        amount: Int,
        description: String?,
        rating: Double?
    ): EmptyResult<DataError.Local> = withContext(Dispatchers.Default) {
        try {
            energyDrinkEntityQueries.updateEnergyDrink(
                id = id,
                name = name,
                amount = amount.toLong(),
                description = description,
                rating = rating,
                updatedAt = Clock.System.now().toEpochMilliseconds()
            )
            Result.Success(Unit)
        } catch (_: Exception) {
            Result.Error(DataError.Local.DOESNT_EXISTS)
        }
    }

    override suspend fun deleteEnergyDrink(id: Long): EmptyResult<DataError.Local> =
        withContext(Dispatchers.Default) {
            try {
                energyDrinkEntityQueries.deleteEnergyDrink(id = id)
                energyDrinkTagQueries.deleteAllTagsForEnergyDrink(energyDrinkId = id)
                Result.Success(Unit)
            } catch (_: Exception) {
                Result.Error(DataError.Local.DOESNT_EXISTS)
            }
        }

    override suspend fun getEnergyDrinkById(id: Long): Result<EnergyDrink, DataError.Local> =
        withContext(Dispatchers.Default) {
            try {
                val energyDrink = energyDrinkEntityQueries.selectById(id = id).awaitAsOne()
                val tags = energyDrinkTagQueries
                    .selectTagsForEnergyDrink(energyDrinkId = id)
                    .awaitAsList()
                    .map { it.toTag() }
                val imagePath = localMetadataQueries
                    .selectByEnergyDrinkId(id)
                    .awaitAsOneOrNull()
                    ?.localImagePath

                Result.Success(energyDrink.toEnergyDrink(tags, imagePath))
            } catch (_: Exception) {
                Result.Error(DataError.Local.DOESNT_EXISTS)
            }
        }

    override fun observeEnergyDrinks(
        query: String,
        sortOption: EnergyDrinkListSortOptions
    ): Flow<List<EnergyDrink>> = combine(
        energyDrinkEntityQueries.observeEnergyDrinks(
            nameQuery = query,
            sortOption = sortOption.name
        ).asFlow().mapToList(Dispatchers.Default),
        observeLocalImagePaths(),
        energyDrinkTagQueries.selectAllRelations()
            .asFlow()
            .mapToList(Dispatchers.Default)
    ) { entities, imagePaths, _ ->
        entities.toEnergyDrinks(imagePaths)
    }

    private fun observeLocalImagePaths(): Flow<Map<Long, String?>> =
        localMetadataQueries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { metadata ->
                metadata.associate { it.energyDrinkId to it.localImagePath }
            }

    private suspend fun List<com.example.EnergyDrinkEntity>.toEnergyDrinks(
        imagePaths: Map<Long, String?>
    ): List<EnergyDrink> = map { entity ->
        val tags = withContext(Dispatchers.Default) {
            energyDrinkTagQueries
                .selectTagsForEnergyDrink(entity.id)
                .awaitAsList()
                .map { it.toTag() }
        }
        entity.toEnergyDrink(tags, imagePaths[entity.id])
    }
}
