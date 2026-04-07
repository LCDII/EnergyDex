package com.example.energydeks.energydrink.domain

import kotlin.time.Instant

data class EnergyDrink(
    val id: Long,
    val name: String,
    val amount: Int,
    val description: String?,
    val rating: Double?,
    val createdAt: Instant,
    val updatedAt: Instant,
    val imagePath:String?,
    val tags: List<String>
)
