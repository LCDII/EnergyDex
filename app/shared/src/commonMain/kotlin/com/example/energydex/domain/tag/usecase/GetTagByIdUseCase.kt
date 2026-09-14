package com.example.energydex.domain.tag.usecase

import com.example.energydex.domain.tag.repository.TagRepository

class GetTagByIdUseCase (
    private val tagRepository: TagRepository
) {
    suspend operator fun invoke(
        id: Long
    ) = tagRepository.getTagById(
        id = id
    )
}