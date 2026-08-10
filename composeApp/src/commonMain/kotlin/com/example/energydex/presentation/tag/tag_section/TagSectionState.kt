package com.example.energydex.presentation.tag.tag_section

import com.example.energydex.core.presentation.UiText

data class TagSectionState(
    val isLoading: Boolean = true,
    val tags: List<TagCardUiModel> = emptyList(),
    val errorMessage: UiText? = null
)
