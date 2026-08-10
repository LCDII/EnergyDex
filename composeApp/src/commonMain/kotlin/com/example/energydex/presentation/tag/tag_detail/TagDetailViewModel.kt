package com.example.energydex.presentation.tag.tag_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.energydex.core.domain.onError
import com.example.energydex.core.domain.onSuccess
import com.example.energydex.core.presentation.toUiText
import com.example.energydex.domain.energydrink.usecase.GetEnergyDrinksForTagUseCase
import com.example.energydex.domain.tag.usecase.DeleteTagUseCase
import com.example.energydex.domain.tag.usecase.GetTagByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TagDetailViewModel(
    private val id: Long,
    private val getTagById: GetTagByIdUseCase,
    private val getEnergyDrinksForTag: GetEnergyDrinksForTagUseCase,
    private val deleteTag: DeleteTagUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(TagDetailState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        getTagById(id).onSuccess { tag ->
            getEnergyDrinksForTag(id).onSuccess { drinks ->
                _state.update {
                    it.copy(isLoading = false, tag = tag, drinks = drinks, errorMessage = null)
                }
            }.onError { error -> showError(error) }
        }.onError { error -> showError(error) }
    }

    private fun showError(error: com.example.energydex.core.domain.DataError.Local) {
        _state.update { it.copy(isLoading = false, errorMessage = error.toUiText()) }
    }

    fun onAction(action: TagDetailAction) {
        when (action) {
            TagDetailAction.OnDeleteClick -> _state.update { it.copy(errorMessage = null) }
            TagDetailAction.OnConfirmDeleteClick -> viewModelScope.launch {
                deleteTag(id).onSuccess {
                    _state.update { it.copy(isDeleted = true) }
                }.onError { error -> showError(error) }
            }
            else -> Unit
        }
    }
}
