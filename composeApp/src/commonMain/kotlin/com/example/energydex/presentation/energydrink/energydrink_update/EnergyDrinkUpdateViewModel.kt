package com.example.energydex.presentation.energydrink.energydrink_update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.energydex.core.domain.onError
import com.example.energydex.core.domain.onSuccess
import com.example.energydex.core.presentation.toUiText
import com.example.energydex.domain.energydrink.usecase.GetEnergyDrinkByIdUseCase
import com.example.energydex.domain.energydrink.usecase.DeleteEnergyDrinkUseCase
import com.example.energydex.domain.energydrink.usecase.UpdateEnergyDrinkUseCase
import com.example.energydex.domain.tag.usecase.ObserveTagsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock

class EnergyDrinkUpdateViewModel(
    private val id: Long,
    private val getEnergyDrinkById: GetEnergyDrinkByIdUseCase,
    private val updateEnergyDrink: UpdateEnergyDrinkUseCase,
    private val observeTags: ObserveTagsUseCase,
    private val deleteEnergyDrink: DeleteEnergyDrinkUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(EnergyDrinkUpdateState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    private var imageChanged = false

    init {
        loadDrink()
        startObservingTags()
    }

    private fun startObservingTags() {
        observeTags()
            .onEach { tags -> _state.update { it.copy(availableTags = tags) } }
            .catch { error -> _state.update { it.copy(errorMessage = error.toUiText()) } }
            .launchIn(viewModelScope)
    }

    fun onAction(action: EnergyDrinkUpdateAction) {
        when (action) {
            is EnergyDrinkUpdateAction.OnNameChange -> {
                _state.update { it.copy(name = action.name, isNameTextValid = action.name.isNotBlank()) }
            }
            is EnergyDrinkUpdateAction.OnDescriptionChange -> {
                _state.update { it.copy(description = action.description) }
            }
            is EnergyDrinkUpdateAction.OnRatingTextChange -> updateRating(action.ratingText)
            is EnergyDrinkUpdateAction.OnImageSelected -> {
                imageChanged = true
                _state.update { it.copy(imagePath = action.path) }
            }
            is EnergyDrinkUpdateAction.OnRemoveImage -> {
                imageChanged = true
                _state.update { it.copy(imagePath = null) }
            }
            is EnergyDrinkUpdateAction.OnTagToggle -> {
                _state.update {
                    val selected = it.selectedTagIds.toMutableSet()
                    if (!selected.add(action.tagId)) selected.remove(action.tagId)
                    it.copy(selectedTagIds = selected)
                }
            }
            is EnergyDrinkUpdateAction.OnSaveClick -> save()
            is EnergyDrinkUpdateAction.OnDeleteClick -> {
                _state.update { it.copy(showDeleteConfirmation = true) }
            }
            is EnergyDrinkUpdateAction.OnConfirmDeleteClick -> delete()
            is EnergyDrinkUpdateAction.OnDeclineDeleteClick -> {
                _state.update { it.copy(showDeleteConfirmation = false) }
            }
            is EnergyDrinkUpdateAction.OnBackClick -> {
                //navigation only; ui responsible for nav;
            }
        }
    }

    private fun loadDrink() = viewModelScope.launch {
        getEnergyDrinkById(id)
            .onSuccess { drink ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        name = drink.name,
                        isNameTextValid = true,
                        description = drink.description.orEmpty(),
                        imagePath = drink.imagePath,
                        ratingText = drink.rating?.toString().orEmpty(),
                        isRatingTextValid = true,
                        rating = drink.rating,
                        amount = drink.amount,
                        initialTagIds = drink.tags.map { it.id }.toSet(),
                        selectedTagIds = drink.tags.map { it.id }.toSet()
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.toUiText()
                    )
                }
            }
    }

    private fun updateRating(value: String) {
        val normalized = value.replace(',', '.').trim()
        val isValid = normalized.isBlank() || (
            normalized.matches(Regex("""^(10(\.0?)?|[0-9](\.[0-9]?)?)$""") ) &&
                normalized.toDoubleOrNull()?.let { it in 0.0..10.0 } == true
            )
        _state.update {
            it.copy(
                ratingText = normalized,
                rating = normalized.toDoubleOrNull(),
                isRatingTextValid = isValid
            )
        }
    }

    private fun save() = viewModelScope.launch {
        val currentState = _state.value
        val name = currentState.name.trim()
        if (currentState.isSaving) return@launch
        if (name.isEmpty() || !currentState.isRatingTextValid) {
            _state.update { it.copy(isNameTextValid = name.isNotEmpty()) }
            return@launch
        }

        _state.update { it.copy(isSaving = true) }
        updateEnergyDrink(
            id = id,
            name = name,
            amount = currentState.amount,
            description = currentState.description,
            rating = currentState.rating,
            updatedAt = Clock.System.now().toEpochMilliseconds(),
            imagePath = currentState.imagePath,
            imageChanged = imageChanged,
            initialTagIds = _state.value.initialTagIds,
            selectedTagIds = _state.value.selectedTagIds
        ).onSuccess {
            _state.update { it.copy(isSaving = false, isSaved = true) }
        }.onError { error ->
            _state.update { it.copy(isSaving = false, errorMessage = error.toUiText()) }
        }
    }

    private fun delete() = viewModelScope.launch {
        if (_state.value.isDeleting) return@launch
        _state.update { it.copy(isDeleting = true) }
        deleteEnergyDrink(id)
            .onSuccess {
                _state.update {
                    it.copy(
                        showDeleteConfirmation = false,
                        isDeleting = false,
                        isDeleted = true
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        showDeleteConfirmation = false,
                        isDeleting = false,
                        errorMessage = error.toUiText()
                    )
                }
            }
    }
}
