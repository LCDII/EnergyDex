package com.example.energydeks.energydrink.data.mappers

import com.example.EnergyDrinkEntity
import com.example.TagEntity
import com.example.energydeks.energydrink.domain.EnergyDrink
import com.example.energydeks.tag.domain.Tag
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

fun TagEntity.toTag(): Tag = Tag(
    id = id,
    name = name,
    color = color
)
