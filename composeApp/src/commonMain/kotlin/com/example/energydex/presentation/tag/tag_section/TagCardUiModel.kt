package com.example.energydex.presentation.tag.tag_section

import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.domain.tag.model.Tag

data class TagCardUiModel(
    val tag: Tag,
    val drinks: List<EnergyDrink>
) {
    val drinkCount: Int get() = drinks.size
}
