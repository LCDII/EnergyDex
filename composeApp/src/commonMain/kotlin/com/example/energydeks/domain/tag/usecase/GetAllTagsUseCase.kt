package com.example.energydeks.domain.tag.usecase

import com.example.energydeks.domain.tag.repository.TagRepository

class GetAllTagsUseCase (
    private val tagRepository: TagRepository
) {
    suspend operator fun invoke() = tagRepository.getAllTags()
}