package com.example.energydex.data.energydrink.image

import com.example.energydex.core.domain.DataError
import com.example.energydex.core.domain.Result

interface ImageStorage {
    suspend fun save(
        sourceUri: String,
        fileName: String
    ): Result<String, DataError.Local>

    suspend fun delete(path: String): Result<Unit, DataError.Local>
}
