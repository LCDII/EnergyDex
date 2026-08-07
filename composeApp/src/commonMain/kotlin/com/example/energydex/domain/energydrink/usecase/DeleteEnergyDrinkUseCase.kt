package com.example.energydex.domain.energydrink.usecase

import com.example.energydex.domain.energydrink.repository.EnergyDrinkRepository
import com.example.energydex.domain.energydrink.repository.EnergyDrinkLocalMetadataRepository
import com.example.energydex.data.energydrink.image.ImageStorage
import com.example.energydex.core.domain.DataError
import com.example.energydex.core.domain.Result

class DeleteEnergyDrinkUseCase(
    private val energyDrinkRepository: EnergyDrinkRepository,
    private val localMetadataRepository: EnergyDrinkLocalMetadataRepository,
    private val imageStorage: ImageStorage
) {
    suspend operator fun invoke(id: Long): Result<Unit, DataError.Local> {
        val imagePath = when (val result = localMetadataRepository.getImagePath(id)) {
            is Result.Error -> null
            is Result.Success -> result.data
        }
        return when (val result = energyDrinkRepository.deleteEnergyDrink(id)) {
            is Result.Error -> result
            is Result.Success -> {
                imagePath?.let { imageStorage.delete(it) }
                result
            }
        }
    }
}
