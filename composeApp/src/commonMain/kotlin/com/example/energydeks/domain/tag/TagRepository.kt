package com.example.energydeks.domain.tag

import com.example.energydeks.core.domain.DataError
import com.example.energydeks.core.domain.EmptyResult
import com.example.energydeks.core.domain.Result

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
    suspend fun getAllTags(): Result<List<Tag>, DataError.Local>
}