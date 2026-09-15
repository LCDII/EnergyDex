package dev.lcdii.energydex.domain.tag.usecase

import dev.lcdii.energydex.domain.tag.repository.TagRepository

class DeleteTagUseCase (
    private val tagRepository: TagRepository
) {
    suspend operator fun invoke(
        id: Long
    ) = tagRepository.deleteTag(
        id = id
    )
}