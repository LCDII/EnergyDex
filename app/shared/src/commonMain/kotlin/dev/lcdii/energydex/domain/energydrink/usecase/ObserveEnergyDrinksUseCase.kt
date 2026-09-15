package dev.lcdii.energydex.domain.energydrink.usecase

import dev.lcdii.energydex.presentation.energydrink.energydrink_section.components.EnergyDrinkListSortOptions
import dev.lcdii.energydex.domain.energydrink.model.EnergyDrink
import dev.lcdii.energydex.domain.energydrink.repository.EnergyDrinkRepository
import kotlinx.coroutines.flow.Flow

class ObserveEnergyDrinksUseCase(
    private val repository: EnergyDrinkRepository
) {
    operator fun invoke(
        query: String = "",
        sortOption: EnergyDrinkListSortOptions = EnergyDrinkListSortOptions.DATE_DESC
    ): Flow<List<EnergyDrink>> = repository.observeEnergyDrinks(query, sortOption)
}
