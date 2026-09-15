package dev.lcdii.energydex.data.energydrink.image

import dev.lcdii.energydex.core.domain.DataError
import dev.lcdii.energydex.core.domain.EmptyResult
import dev.lcdii.energydex.core.domain.Result

class WasmImageStorage : ImageStorage {
    override suspend fun save(
        sourceUri: String,
        fileName: String
    ): Result<String, DataError.Local> = Result.Success(sourceUri)

    override suspend fun delete(path: String): EmptyResult<DataError.Local> =
        Result.Success(Unit)
}
