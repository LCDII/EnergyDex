package com.example.energydeks.domain.tag.usecase

data class TagUseCases(
    val create: CreateTagUseCase,
    val update: UpdateTagUseCase,
    val delete: DeleteTagUseCase,
    val getById: GetTagByIdUseCase,
    val getAll: GetAllTagsUseCase
)
