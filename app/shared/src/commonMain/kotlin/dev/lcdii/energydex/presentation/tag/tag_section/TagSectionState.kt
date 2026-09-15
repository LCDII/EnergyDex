package dev.lcdii.energydex.presentation.tag.tag_section

import dev.lcdii.energydex.core.presentation.UiText
import dev.lcdii.energydex.domain.tag.model.TagWithEnergyDrinks

data class TagSectionState(
    val isLoading: Boolean = true,
    val tags: List<TagWithEnergyDrinks> = emptyList(),
    val errorMessage: UiText? = null
)
