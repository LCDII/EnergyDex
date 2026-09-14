package com.example.energydex.domain.energydrink.usecase

import com.example.energydex.domain.energydrink.repository.EnergyDrinkRepository
import com.example.energydex.domain.energydrink.repository.EnergyDrinkLocalMetadataRepository
import com.example.energydex.data.energydrink.image.ImageStorage
import com.example.energydex.domain.energydrink.repository.EnergyDrinkTagRepository
import com.example.energydex.core.domain.DataError
import com.example.energydex.core.domain.EmptyResult
import com.example.energydex.core.domain.Result
import kotlin.time.Clock

class UpdateEnergyDrinkUseCase(
    private val energyDrinkRepository: EnergyDrinkRepository
    ,private val localMetadataRepository: EnergyDrinkLocalMetadataRepository
    ,private val imageStorage: ImageStorage
    ,private val energyDrinkTagRepository: EnergyDrinkTagRepository
) {
    suspend operator fun invoke(
        id: Long,
        name: String,
        amount: Int,
        description: String?,
        rating: Double?,
        updatedAt: Long,
        imagePath: String?,
        imageChanged: Boolean,
        initialTagIds: Set<Long>,
        selectedTagIds: Set<Long>
    ): EmptyResult<DataError.Local> {
        val oldImagePath = when (val result = localMetadataRepository.getImagePath(id)) {
            is Result.Error -> return Result.Error(result.error)
            is Result.Success -> result.data
        }
        val newImagePath = if (!imageChanged || imagePath == null) {
            imagePath
        } else {
            when (val result = imageStorage.save(
                sourceUri = imagePath,
                fileName = "energy_drink_${id}_${Clock.System.now().toEpochMilliseconds()}.jpg"
            )) {
                is Result.Error -> return Result.Error(result.error)
                is Result.Success -> result.data
            }
        }

        val updateResult = energyDrinkRepository.updateEnergyDrink(
            id = id,
            name = name,
            amount = amount,
            description = description,
            rating = rating
        )
        if (updateResult is Result.Error) {
            if (imageChanged && newImagePath != null && newImagePath != imagePath) {
                imageStorage.delete(newImagePath)
            }
            return Result.Error(updateResult.error)
        }

        val metadataResult = if (!imageChanged) {
            Result.Success(Unit)
        } else if (newImagePath == null) {
            localMetadataRepository.deleteImagePath(id)
        } else {
            localMetadataRepository.saveImagePath(id, newImagePath)
        }

        if (metadataResult is Result.Error) {
            return Result.Error(metadataResult.error)
        }
        for (tagId in initialTagIds - selectedTagIds) {
            val result = energyDrinkTagRepository.detachTagFromEnergyDrink(tagId, id)
            if (result is Result.Error) return Result.Error(result.error)
        }
        for (tagId in selectedTagIds - initialTagIds) {
            val result = energyDrinkTagRepository.attachTagToEnergyDrink(tagId, id)
            if (result is Result.Error) return Result.Error(result.error)
        }
        if (imageChanged && oldImagePath != null && oldImagePath != newImagePath) {
            imageStorage.delete(oldImagePath)
        }
        return Result.Success(Unit)
    }
}
