package com.example.energydex.presentation.tag.tag_create_edit

sealed interface TagCreateEditAction {
    data class OnNameChange(val name: String) : TagCreateEditAction
    data class OnColorSelected(val color: String) : TagCreateEditAction
    data object OnSaveClick : TagCreateEditAction
}
