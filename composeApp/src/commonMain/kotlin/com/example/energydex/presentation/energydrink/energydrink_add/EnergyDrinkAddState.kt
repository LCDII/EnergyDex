package com.example.energydex.presentation.energydrink.energydrink_add

import com.example.energydex.core.presentation.UiText

data class EnergyDrinkAddState(
    val name: String = "",
    val isNameTextValid: Boolean = false,
    val description: String = "",
    val imagePath: String? = null,
    val ratingText: String = "",
    val isRatingTextValid: Boolean = false,
    val rating: Double? = null,
    val amount: Int = 1,
    val isLoadingImage: Boolean = false,
    val isSaved: Boolean = false,
    val isSaving: Boolean = false,
    val errorMessage: UiText? = null
)