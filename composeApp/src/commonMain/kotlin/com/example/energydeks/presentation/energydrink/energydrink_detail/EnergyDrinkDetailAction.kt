package com.example.energydeks.presentation.energydrink.energydrink_detail

interface EnergyDrinkDetailAction {

    data object OnUpdateClick: EnergyDrinkDetailAction
    data object OnDeleteClick: EnergyDrinkDetailAction
    data object OnConfirmDeleteClick: EnergyDrinkDetailAction
    data object OnDeclineDeleteClick: EnergyDrinkDetailAction
    data object OnBackClick: EnergyDrinkDetailAction
}