package com.example.energydex.domain.energydrink.usecase

import com.example.energydex.core.domain.EnergyDrinkListSortOptions
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.domain.energydrink.repository.EnergyDrinkTagRepository
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
