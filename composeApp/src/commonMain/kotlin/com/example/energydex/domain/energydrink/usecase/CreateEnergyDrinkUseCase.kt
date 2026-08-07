package com.example.energydex.domain.energydrink.usecase

import com.example.energydex.domain.energydrink.repository.EnergyDrinkRepository
import com.example.energydex.domain.energydrink.repository.EnergyDrinkLocalMetadataRepository
import com.example.energydex.core.domain.DataError
import com.example.energydex.core.domain.Result
import com.example.energydex.data.energydrink.image.ImageStorage
import kotlin.time.Clock

class CreateEnergyDrinkUseCase(
    private val energyDrinkRepository: EnergyDrinkRepository,
    private val localMetadataRepository: EnergyDrinkLocalMetadataRepository,
    private val imageStorage: ImageStorage
) {
    suspend operator fun invoke(
        name: String,
        amount: Int,
        description: String?,
        rating: Double?,
        imagePath: String?
    ): Result<Long, DataError.Local> {
        val localImagePath = if (imagePath == null) {
            null
        } else {
            when (val result = imageStorage.save(
                sourceUri = imagePath,
                fileName = "energy_drink_${Clock.System.now().toEpochMilliseconds()}.jpg"
            )) {
                is Result.Error -> return Result.Error(result.error)
                is Result.Success -> result.data
            }
        }

        return when (val result = energyDrinkRepository.createEnergyDrink(
            name = name,
            amount = amount,
            description = description,
            rating = rating
        )) {
            is Result.Error -> {
                localImagePath?.let { imageStorage.delete(it) }
                result
            }
            is Result.Success -> {
                if (localImagePath == null) {
                    result
                } else {
                    when (val metadataResult = localMetadataRepository.saveImagePath(
                        energyDrinkId = result.data,
                        localImagePath = localImagePath
                    )) {
                        is Result.Error -> {
                            imageStorage.delete(localImagePath)
                            Result.Error(metadataResult.error)
                        }
                        is Result.Success -> result
                    }
                }
            }
        }
    }

}
