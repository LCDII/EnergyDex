package dev.lcdii.energydex.data.energydrink.image

import dev.lcdii.energydex.core.domain.DataError
import dev.lcdii.energydex.core.domain.Result
import dev.lcdii.energydex.core.domain.EmptyResult

interface ImageStorage {
    suspend fun save(
        sourceUri: String,
        fileName: String
    ): Result<String, DataError.Local>

    suspend fun delete(path: String): EmptyResult<DataError.Local>
}
