package com.example.energydeks.app

import kotlinx.serialization.Serializable

sealed interface Route{
    @Serializable
    data object EnergyDexGraph: Route
    @Serializable
    data class EnergyDrinkDetail(val id: String): Route
    @Serializable
    data class TagDetail(val id: String): Route
}