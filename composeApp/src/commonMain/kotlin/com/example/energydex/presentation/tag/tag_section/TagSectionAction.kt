package com.example.energydex.presentation.tag.tag_section

import com.example.energydex.domain.tag.model.Tag

sealed interface TagSectionAction {
    data class OnTagClick(val tag: Tag) : TagSectionAction
    data object OnCreateTagClick : TagSectionAction
}
