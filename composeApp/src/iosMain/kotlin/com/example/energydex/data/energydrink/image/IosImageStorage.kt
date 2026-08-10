package com.example.energydex.data.energydrink.image

import com.example.energydex.core.domain.DataError
import com.example.energydex.core.domain.EmptyResult
import com.example.energydex.core.domain.Result
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSData
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfURL
import platform.Foundation.writeToFile

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
class IosImageStorage : ImageStorage {
    override suspend fun save(
        sourceUri: String,
        fileName: String
    ): Result<String, DataError.Local> = try {
        val directory = documentsDirectory().toString() + "/EnergyDex/energy-drinks"
        NSFileManager.defaultManager.createDirectoryAtPath(
            path = directory,
            withIntermediateDirectories = true,
            attributes = null,
            error = null
        )

        val source = NSURL(string = sourceUri)
        val data = NSData.dataWithContentsOfURL(source)
            ?: return Result.Error(DataError.Local.DOESNT_EXISTS)
        val target = "$directory/$fileName"
        if (!data.writeToFile(target, atomically = true)) {
            return Result.Error(DataError.Local.UNKNOWN)
        }
        Result.Success(NSURL.fileURLWithPath(target).absoluteString ?: target)
    } catch (_: Exception) {
        Result.Error(DataError.Local.UNKNOWN)
    }

    override suspend fun delete(path: String): EmptyResult<DataError.Local> = try {
        val filePath = NSURL(string = path).path
            ?: return Result.Error(DataError.Local.DOESNT_EXISTS)
        if (NSFileManager.defaultManager.removeItemAtPath(filePath, error = null)) {
            Result.Success(Unit)
        } else {
            Result.Error(DataError.Local.DOESNT_EXISTS)
        }
    } catch (_: Exception) {
        Result.Error(DataError.Local.UNKNOWN)
    }

    private fun documentsDirectory(): String =
        NSSearchPathForDirectoriesInDomains(
            directory = NSDocumentDirectory,
            domainMask = NSUserDomainMask,
            expandTilde = true
        ).first() as String
}
