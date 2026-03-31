package com.example.energydeks.energydrink.domain

import com.example.energydeks.tag.domain.Tag

data class EnergyDrink(
    val id: Long,
    val name: String,
    val description: String?,
    val rating: Double?,
    val imagePath:String?,
    val tags: List<String>
)
