package com.example.energydex.data.energydrink.image

import com.example.energydex.core.domain.DataError
import com.example.energydex.core.domain.EmptyResult
import com.example.energydex.core.domain.Result

class WasmImageStorage : ImageStorage {
    override suspend fun save(
        sourceUri: String,
        fileName: String
    ): Result<String, DataError.Local> = Result.Success(sourceUri)

    override suspend fun delete(path: String): EmptyResult<DataError.Local> =
        Result.Success(Unit)
}
