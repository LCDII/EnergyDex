package com.example.energydeks.domain.tag.usecase

import com.example.energydeks.domain.tag.repository.TagRepository

class DeleteTagUseCase (
    private val tagRepository: TagRepository
) {
    suspend operator fun invoke(
        id: Long
    ) = tagRepository.deleteTag(
        id = id
    )
}