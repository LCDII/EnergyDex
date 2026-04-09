package com.example.energydeks.data.tag.repository

import com.example.TagEntityQueries
import com.example.energydeks.core.domain.DataError
import com.example.energydeks.core.domain.EmptyResult
import com.example.energydeks.core.domain.Result
import com.example.energydeks.data.tag.mappers.toTag
import com.example.energydeks.domain.tag.Tag
import com.example.energydeks.domain.tag.TagRepository

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

    override suspend fun getAllTags(): Result<List<Tag>, DataError.Local> =
        try {
            val tags = tagEntityQueries.selectAllTags().executeAsList().map { it.toTag() }
            Result.Success(tags)
        } catch (e: Exception) {
            Result.Error(DataError.Local.DOESNT_EXISTS)
        }
}