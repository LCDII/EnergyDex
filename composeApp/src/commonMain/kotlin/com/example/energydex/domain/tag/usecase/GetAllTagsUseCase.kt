package com.example.energydex.domain.tag.usecase

import com.example.energydex.domain.tag.repository.TagRepository

class GetAllTagsUseCase (
    private val tagRepository: TagRepository
) {
    suspend operator fun invoke() = tagRepository.getAllTags()
}