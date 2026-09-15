package dev.lcdii.energydex.data.energydrink.mappers

import dev.lcdii.energydex.database.EnergyDrinkEntity
import dev.lcdii.energydex.domain.energydrink.model.EnergyDrink
import dev.lcdii.energydex.domain.tag.model.Tag
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
