package com.example.energydex.data.energydrink.image

import android.content.Context
import android.net.Uri
import com.example.energydex.core.domain.DataError
import com.example.energydex.core.domain.EmptyResult
import com.example.energydex.core.domain.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class AndroidImageStorage(
    private val context: Context
) : ImageStorage {
    override suspend fun save(
        sourceUri: String,
        fileName: String
    ): Result<String, DataError.Local> = withContext(Dispatchers.IO) {
        try {
            val directory = File(context.filesDir, "energy-drinks").apply { mkdirs() }
            val target = File(directory, fileName)
            context.contentResolver.openInputStream(Uri.parse(sourceUri))?.use { input ->
                target.outputStream().use { output -> input.copyTo(output) }
            } ?: return@withContext Result.Error(DataError.Local.DOESNT_EXISTS)
            Result.Success(target.toURI().toString())
        } catch (_: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun delete(path: String): EmptyResult<DataError.Local> = withContext(Dispatchers.IO) {
        try {
            val deleted = Uri.parse(path).path?.let(::File)?.delete() ?: false
            if (deleted) Result.Success(Unit) else Result.Error(DataError.Local.DOESNT_EXISTS)
        } catch (_: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }
}
