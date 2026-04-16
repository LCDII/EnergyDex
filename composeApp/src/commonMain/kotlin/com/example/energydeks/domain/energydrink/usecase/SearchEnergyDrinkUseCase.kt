package com.example.energydeks.domain.energydrink.usecase

import com.example.energydeks.core.domain.EnergyDrinkListSortOptions
import com.example.energydeks.domain.energydrink.model.EnergyDrink
import com.example.energydeks.domain.energydrink.repository.EnergyDrinkRepository
import kotlinx.coroutines.flow.Flow

class SearchEnergyDrinksUseCase(
    private val repository: EnergyDrinkRepository
) {

    suspend operator fun invoke(
        query: String,
        sortOption: EnergyDrinkListSortOptions
    ) = repository.searchEnergyDrink(
            query = query,
            sortOption = sortOption
    )
}