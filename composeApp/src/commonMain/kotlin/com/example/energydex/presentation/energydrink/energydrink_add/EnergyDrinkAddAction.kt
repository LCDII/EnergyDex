package com.example.energydex.presentation.energydrink.energydrink_add

interface EnergyDrinkAddAction {
    data class OnNameChange(val name: String): EnergyDrinkAddAction
    data class OnDescriptionChange(val description: String): EnergyDrinkAddAction
    data class OnRatingTextChange(val ratingText: String): EnergyDrinkAddAction

    data class OnImageSelected(val path: String?) : EnergyDrinkAddAction
    data object OnRemoveImage: EnergyDrinkAddAction
    data class OnTagToggle(val tagId: Long): EnergyDrinkAddAction
    data object OnSaveClick: EnergyDrinkAddAction
    data object OnBackClick: EnergyDrinkAddAction
}
