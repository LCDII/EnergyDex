package com.example.energydeks.data.tag.mappers

import com.example.TagEntity
import com.example.energydeks.domain.tag.model.Tag

fun TagEntity.toTag(): Tag = Tag(
    id = id,
    name = name,
    color = color
)