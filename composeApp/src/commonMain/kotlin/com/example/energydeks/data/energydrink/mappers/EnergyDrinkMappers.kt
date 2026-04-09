package com.example.energydeks.data.energydrink.mappers

import com.example.EnergyDrinkEntity
import com.example.energydeks.domain.energydrink.EnergyDrink
import com.example.energydeks.domain.tag.Tag
import kotlin.time.Instant

fun EnergyDrinkEntity.toEnergyDrink(tags: List<Tag>): EnergyDrink = EnergyDrink(
    id = id,
    name = name,
    amount = amount.toInt(),
    description = description,
    rating = rating?.toDouble(),
    createdAt = Instant.fromEpochMilliseconds(createdAt),
    updatedAt = Instant.fromEpochMilliseconds(updatedAt),
    imagePath = imagePath,
    tags = tags
)