package com.example.energydex.domain.tag.repository

import com.example.energydex.core.domain.DataError
import com.example.energydex.core.domain.EmptyResult
import com.example.energydex.core.domain.Result
import com.example.energydex.domain.tag.model.Tag
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
