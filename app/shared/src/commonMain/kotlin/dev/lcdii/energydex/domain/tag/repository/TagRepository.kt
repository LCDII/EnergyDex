package dev.lcdii.energydex.domain.tag.repository

import dev.lcdii.energydex.core.domain.DataError
import dev.lcdii.energydex.core.domain.EmptyResult
import dev.lcdii.energydex.core.domain.Result
import dev.lcdii.energydex.domain.tag.model.Tag
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    suspend fun createTag(
        name: String,
        color: String,
    ): EmptyResult<DataError.Local>
    suspend fun updateTag(
        id: Long,
        name: String,
        color: String,
    ): EmptyResult<DataError.Local>
    suspend fun deleteTag(id: Long): EmptyResult<DataError.Local>
    suspend fun getTagById(id: Long): Result<Tag, DataError.Local>
    fun observeAllTags(): Flow<List<Tag>>
}
