package dev.lcdii.energydex.presentation.tag.tag_create_edit

import dev.lcdii.energydex.core.presentation.UiText
import dev.lcdii.energydex.core.presentation.TagOrangeValue

data class TagCreateEditState(
    val isLoading: Boolean = false,
    val name: String = "",
    val color: String = TagOrangeValue,
    val isNameValid: Boolean = true,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val errorMessage: UiText? = null
)
