package dev.lcdii.energydex.data.tag.repository

import dev.lcdii.energydex.database.TagEntityQueries
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.async.coroutines.awaitAsOne
import app.cash.sqldelight.coroutines.mapToList
import dev.lcdii.energydex.core.domain.DataError
import dev.lcdii.energydex.core.domain.EmptyResult
import dev.lcdii.energydex.core.domain.Result
import dev.lcdii.energydex.data.tag.mappers.toTag
import dev.lcdii.energydex.domain.tag.model.Tag
import dev.lcdii.energydex.domain.tag.repository.TagRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TagRepositoryImpl(
    private val tagEntityQueries: TagEntityQueries
): TagRepository {

    override suspend fun createTag(
        name: String,
        color: String
    ): EmptyResult<DataError.Local> =
        try{
            tagEntityQueries.insertTag(
                name = name,
                color = color
            )
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.ALREADY_EXISTS)
        }

    override suspend fun updateTag(
        id: Long,
        name: String,
        color: String
    ): EmptyResult<DataError.Local> =
        try {
            tagEntityQueries.updateTag(
                id = id,
                name = name,
                color = color
            )
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.DOESNT_EXISTS)
        }

    override suspend fun deleteTag(id: Long): EmptyResult<DataError.Local> =
        try {
            tagEntityQueries.deleteTag(id = id)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.DOESNT_EXISTS)
        }

    override suspend fun getTagById(id: Long): Result<Tag, DataError.Local> =
        try {
            val tag = tagEntityQueries.selectTagById(id = id).awaitAsOne()
            Result.Success(tag.toTag())
        } catch (e: Exception) {
            Result.Error(DataError.Local.DOESNT_EXISTS)
        }

    override fun observeAllTags(): Flow<List<Tag>> =
        tagEntityQueries.selectAllTags()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { tags -> tags.map { it.toTag() } }
}
