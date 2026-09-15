package dev.lcdii.energydex.domain.energydrink.usecase

import dev.lcdii.energydex.presentation.energydrink.energydrink_section.components.EnergyDrinkListSortOptions
import dev.lcdii.energydex.domain.energydrink.model.EnergyDrink
import dev.lcdii.energydex.domain.energydrink.repository.EnergyDrinkTagRepository
import kotlinx.coroutines.flow.Flow

class ObserveEnergyDrinksForTagUseCase(
    private val repository: EnergyDrinkTagRepository
) {
    operator fun invoke(
        tagId: Long,
        query: String = "",
        sortOption: EnergyDrinkListSortOptions = EnergyDrinkListSortOptions.DATE_DESC
    ): Flow<List<EnergyDrink>> = repository.observeEnergyDrinksForTag(
        tagId = tagId,
        query = query,
        sortOption = sortOption
    )
}
