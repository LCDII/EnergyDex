package com.example.energydex.presentation.energydrink.energydrink_add

interface EnergyDrinkAddAction {
    data class OnNameChange(val name: String): EnergyDrinkAddAction
    data class OnDescriptionChange(val description: String?): EnergyDrinkAddAction
    data class OnRatingTextChange(val ratingText: String): EnergyDrinkAddAction

    data class OnStarClick(val value: Double) : EnergyDrinkAddAction
    data object OnPickImage: EnergyDrinkAddAction
    data class OnImageSelected(val path: String?) : EnergyDrinkAddAction
    data object OnSaveClick: EnergyDrinkAddAction
    data object OnBackClick: EnergyDrinkAddAction
}