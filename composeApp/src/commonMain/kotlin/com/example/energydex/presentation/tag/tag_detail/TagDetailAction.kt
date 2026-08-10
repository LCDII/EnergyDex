package com.example.energydex.presentation.tag.tag_detail

sealed interface TagDetailAction {
    data object OnBackClick : TagDetailAction
    data object OnEditClick : TagDetailAction
    data object OnDeleteClick : TagDetailAction
    data object OnConfirmDeleteClick : TagDetailAction
    data object OnDeclineDeleteClick : TagDetailAction
}
