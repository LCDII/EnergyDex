package com.example.energydex.domain.energydrink.usecase

import com.example.energydex.presentation.energydrink.energydrink_section.components.EnergyDrinkListSortOptions
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.domain.energydrink.repository.EnergyDrinkRepository
import kotlinx.coroutines.flow.Flow

class ObserveEnergyDrinksUseCase(
    private val repository: EnergyDrinkRepository
) {
    operator fun invoke(
        query: String = "",
        sortOption: EnergyDrinkListSortOptions = EnergyDrinkListSortOptions.DATE_DESC
    ): Flow<List<EnergyDrink>> = repository.observeEnergyDrinks(query, sortOption)
}
