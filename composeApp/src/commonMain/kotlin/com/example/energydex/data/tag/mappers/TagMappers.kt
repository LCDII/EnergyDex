package com.example.energydex.data.tag.mappers

import com.example.TagEntity
import com.example.energydex.domain.tag.model.Tag

fun TagEntity.toTag(): Tag = Tag(
    id = id,
    name = name,
    color = color
)