package com.example.energydeks.domain.tag.usecase

import com.example.energydeks.domain.tag.repository.TagRepository

class GetTagByIdUseCase (
    private val tagRepository: TagRepository
) {
    suspend operator fun invoke(
        id: Long
    ) = tagRepository.getTagById(
        id = id
    )
}