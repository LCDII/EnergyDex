package dev.lcdii.energydex.data.tag.mappers

import dev.lcdii.energydex.database.TagEntity
import dev.lcdii.energydex.domain.tag.model.Tag

fun TagEntity.toTag(): Tag = Tag(
    id = id,
    name = name,
    color = color
)