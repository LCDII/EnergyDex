package com.example.energydex.domain.tag.usecase

import com.example.energydex.domain.tag.repository.TagRepository

class ObserveTagsUseCase(
    private val tagRepository: TagRepository
) {
    operator fun invoke() = tagRepository.observeAllTags()
}
