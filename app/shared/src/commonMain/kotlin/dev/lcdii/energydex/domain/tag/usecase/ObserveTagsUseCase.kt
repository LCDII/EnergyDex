package dev.lcdii.energydex.domain.tag.usecase

import dev.lcdii.energydex.domain.tag.repository.TagRepository

class ObserveTagsUseCase(
    private val tagRepository: TagRepository
) {
    operator fun invoke() = tagRepository.observeAllTags()
}
