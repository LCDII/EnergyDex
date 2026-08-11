package com.example.energydex.domain.tag.model

import com.example.energydex.domain.energydrink.model.EnergyDrink

data class TagWithEnergyDrinks(
    val tag: Tag,
    val drinks: List<EnergyDrink>
)
