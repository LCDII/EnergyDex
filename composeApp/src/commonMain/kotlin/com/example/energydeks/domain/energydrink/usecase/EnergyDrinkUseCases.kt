package com.example.energydeks.domain.energydrink.usecase

data class EnergyDrinkUseCases(
    val create: CreateEnergyDrinkUseCase,
    val update: UpdateEnergyDrinkUseCase,
    val delete: DeleteEnergyDrinkUseCase,
    val getById: GetEnergyDrinkByIdUSeCase,
    val getAll: GetAllEnergyDrinksUseCase,
    val attachTag: AttachTagToEnergyDrinkUseCase,
    val detachTag: DetachTagFromEnergyDrinkUseCase
)
