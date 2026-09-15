package dev.lcdii.energydex.domain.energydrink.usecase

import dev.lcdii.energydex.domain.energydrink.repository.EnergyDrinkRepository
import dev.lcdii.energydex.domain.energydrink.repository.EnergyDrinkLocalMetadataRepository
import dev.lcdii.energydex.data.energydrink.image.ImageStorage
import dev.lcdii.energydex.core.domain.DataError
import dev.lcdii.energydex.core.domain.EmptyResult
import dev.lcdii.energydex.core.domain.Result

class DeleteEnergyDrinkUseCase(
    private val energyDrinkRepository: EnergyDrinkRepository,
    private val localMetadataRepository: EnergyDrinkLocalMetadataRepository,
    private val imageStorage: ImageStorage
) {
    suspend operator fun invoke(id: Long): EmptyResult<DataError.Local> {
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
