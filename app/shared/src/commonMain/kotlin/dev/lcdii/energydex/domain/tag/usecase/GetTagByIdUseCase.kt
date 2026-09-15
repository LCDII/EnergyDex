package dev.lcdii.energydex.domain.tag.usecase

import dev.lcdii.energydex.domain.tag.repository.TagRepository

class GetTagByIdUseCase (
    private val tagRepository: TagRepository
) {
    suspend operator fun invoke(
        id: Long
    ) = tagRepository.getTagById(
        id = id
    )
}