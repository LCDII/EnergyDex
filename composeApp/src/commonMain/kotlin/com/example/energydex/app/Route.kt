package com.example.energydex.app

import kotlinx.serialization.Serializable

sealed interface Route{
    @Serializable
    data object EnergyDexGraph: Route
    @Serializable
    data object MainScreen: Route
    @Serializable
    data class EnergyDrinkDetail(val id: Long): Route
    @Serializable
    data class TagDetail(val id: Long): Route
    @Serializable
    data object CreateEnergyDrink: Route
    @Serializable
    data object CreateTag: Route
    @Serializable
    data class UpdateEnergyDrink(val id: Long): Route
    @Serializable
    data class UpdateTag(val id: Long): Route
}