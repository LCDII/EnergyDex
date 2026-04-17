package com.example.energydex.domain.tag.usecase

import com.example.energydex.domain.tag.repository.TagRepository

class CreateTagUseCase(
    private val tagRepository: TagRepository
) {
    suspend operator fun invoke(
        name: String,
        color: String
    ) = tagRepository.createTag(
        name = name,
        color = color
    )
}