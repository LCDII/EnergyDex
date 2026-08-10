package com.example.energydex.data.tag.repository

import com.example.TagEntityQueries
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.energydex.core.domain.DataError
import com.example.energydex.core.domain.EmptyResult
import com.example.energydex.core.domain.Result
import com.example.energydex.data.tag.mappers.toTag
import com.example.energydex.domain.tag.model.Tag
import com.example.energydex.domain.tag.repository.TagRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
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
            val tag = tagEntityQueries.selectTagById(id = id).executeAsOne()
            Result.Success(tag.toTag())
        } catch (e: Exception) {
            Result.Error(DataError.Local.DOESNT_EXISTS)
        }

    override fun observeAllTags(): Flow<List<Tag>> =
        tagEntityQueries.selectAllTags()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { tags -> tags.map { it.toTag() } }
}
