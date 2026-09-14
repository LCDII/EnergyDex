package com.example.energydex.domain.tag.usecase

import com.example.energydex.domain.tag.repository.TagRepository

class UpdateTagUseCase (
    private val tagRepository: TagRepository
) {
    suspend operator fun invoke(
        id: Long,
        name: String,
        color: String
    ) = tagRepository.updateTag(
        id = id,
        name = name,
        color = color
    )
}