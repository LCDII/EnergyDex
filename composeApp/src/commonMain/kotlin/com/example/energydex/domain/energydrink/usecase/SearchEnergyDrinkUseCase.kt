package com.example.energydex.domain.energydrink.usecase

import com.example.energydex.core.domain.EnergyDrinkListSortOptions
import com.example.energydex.domain.energydrink.repository.EnergyDrinkRepository

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