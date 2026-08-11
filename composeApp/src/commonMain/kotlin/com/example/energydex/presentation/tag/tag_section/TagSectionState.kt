package com.example.energydex.presentation.tag.tag_section

import com.example.energydex.core.presentation.UiText
import com.example.energydex.domain.tag.model.TagWithEnergyDrinks

data class TagSectionState(
    val isLoading: Boolean = true,
    val tags: List<TagWithEnergyDrinks> = emptyList(),
    val errorMessage: UiText? = null
)
