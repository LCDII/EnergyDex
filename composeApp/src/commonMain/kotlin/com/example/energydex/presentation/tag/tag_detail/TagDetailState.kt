package com.example.energydex.presentation.tag.tag_detail

import com.example.energydex.core.presentation.UiText
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.domain.tag.model.Tag

data class TagDetailState(
    val isLoading: Boolean = true,
    val tag: Tag? = null,
    val drinks: List<EnergyDrink> = emptyList(),
    val errorMessage: UiText? = null,
    val isDeleted: Boolean = false
)
