package dev.lcdii.energydex.presentation.tag.tag_create_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.lcdii.energydex.core.domain.onError
import dev.lcdii.energydex.core.domain.onSuccess
import dev.lcdii.energydex.core.presentation.toUiText
import dev.lcdii.energydex.domain.tag.usecase.CreateTagUseCase
import dev.lcdii.energydex.domain.tag.usecase.GetTagByIdUseCase
import dev.lcdii.energydex.domain.tag.usecase.UpdateTagUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TagCreateEditViewModel(
    private val tagId: Long?,
    private val getTagById: GetTagByIdUseCase,
    private val createTag: CreateTagUseCase,
    private val updateTag: UpdateTagUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(TagCreateEditState(isLoading = tagId != null))
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    init {
        if (tagId != null) loadTag(tagId)
    }

    fun onAction(action: TagCreateEditAction) {
        when (action) {
            is TagCreateEditAction.OnNameChange -> _state.update {
                it.copy(
                    name = action.name,
                    isNameValid = action.name.isNotBlank(),
                    errorMessage = null
                )
            }
            is TagCreateEditAction.OnColorSelected -> _state.update {
                it.copy(color = action.color)
            }
            TagCreateEditAction.OnSaveClick -> save()
        }
    }

    private fun loadTag(id: Long) = viewModelScope.launch {
        getTagById(id).onSuccess { tag ->
            _state.update {
                it.copy(isLoading = false, name = tag.name, color = tag.color)
            }
        }.onError { error ->
            _state.update { it.copy(isLoading = false, errorMessage = error.toUiText()) }
        }
    }

    private fun save() = viewModelScope.launch {
        val current = _state.value
        val name = current.name.trim()
        if (current.isSaving) return@launch
        if (name.isEmpty()) {
            _state.update { it.copy(isNameValid = false) }
            return@launch
        }

        _state.update { it.copy(isSaving = true) }
        val result = if (tagId == null) {
            createTag(name, current.color)
        } else {
            updateTag(tagId, name, current.color)
        }
        result.onSuccess {
            _state.update { it.copy(isSaving = false, isSaved = true) }
        }.onError { error ->
            _state.update { it.copy(isSaving = false, errorMessage = error.toUiText()) }
        }
    }
}
