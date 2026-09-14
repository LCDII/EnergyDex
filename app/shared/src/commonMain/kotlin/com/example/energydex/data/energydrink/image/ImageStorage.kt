package com.example.energydex.data.energydrink.image

import com.example.energydex.core.domain.DataError
import com.example.energydex.core.domain.Result
import com.example.energydex.core.domain.EmptyResult

interface ImageStorage {
    suspend fun save(
        sourceUri: String,
        fileName: String
    ): Result<String, DataError.Local>

    suspend fun delete(path: String): EmptyResult<DataError.Local>
}
