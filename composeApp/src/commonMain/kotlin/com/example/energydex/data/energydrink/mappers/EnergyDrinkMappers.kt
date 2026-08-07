package com.example.energydex.data.energydrink.mappers

import com.example.EnergyDrinkEntity
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.domain.tag.model.Tag
import kotlin.time.Instant

fun EnergyDrinkEntity.toEnergyDrink(
    tags: List<Tag>,
    imagePath: String?
): EnergyDrink = EnergyDrink(
    id = id,
    name = name,
    amount = amount.toInt(),
    description = description,
    rating = rating,
    createdAt = Instant.fromEpochMilliseconds(createdAt),
    updatedAt = Instant.fromEpochMilliseconds(updatedAt),
    imagePath = imagePath,
    tags = tags
)
