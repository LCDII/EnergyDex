package dev.lcdii.energydex.presentation.tag.tag_section

import dev.lcdii.energydex.domain.tag.model.Tag

sealed interface TagSectionAction {
    data class OnTagClick(val tag: Tag) : TagSectionAction
    data object OnCreateTagClick : TagSectionAction
}
