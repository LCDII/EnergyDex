package com.example.energydex.presentation.energydrink.energydrink_update

interface EnergyDrinkUpdateAction {
    data class OnNameChange(val name: String) : EnergyDrinkUpdateAction
    data class OnDescriptionChange(val description: String) : EnergyDrinkUpdateAction
    data class OnRatingTextChange(val ratingText: String) : EnergyDrinkUpdateAction
    data class OnImageSelected(val path: String?) : EnergyDrinkUpdateAction
    data object OnRemoveImage : EnergyDrinkUpdateAction
    data class OnTagToggle(val tagId: Long) : EnergyDrinkUpdateAction
    data object OnSaveClick : EnergyDrinkUpdateAction
    data object OnDeleteClick : EnergyDrinkUpdateAction
    data object OnConfirmDeleteClick : EnergyDrinkUpdateAction
    data object OnDeclineDeleteClick : EnergyDrinkUpdateAction
    data object OnBackClick : EnergyDrinkUpdateAction
}
