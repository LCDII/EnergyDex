package dev.lcdii.energydex.domain.tag.model

import dev.lcdii.energydex.domain.energydrink.model.EnergyDrink

data class TagWithEnergyDrinks(
    val tag: Tag,
    val drinks: List<EnergyDrink>
)
