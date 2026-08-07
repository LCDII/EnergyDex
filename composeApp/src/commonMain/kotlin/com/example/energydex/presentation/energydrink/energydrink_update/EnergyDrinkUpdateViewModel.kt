package com.example.energydex.presentation.energydrink.energydrink_update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.energydex.core.domain.onError
import com.example.energydex.core.domain.onSuccess
import com.example.energydex.core.presentation.toUiText
import com.example.energydex.domain.energydrink.usecase.GetEnergyDrinkByIdUseCase
import com.example.energydex.domain.energydrink.usecase.UpdateEnergyDrinkUseCase
import com.example.energydex.presentation.energydrink.energydrink_add.EnergyDrinkAddAction
import com.example.energydex.presentation.energydrink.energydrink_add.EnergyDrinkAddState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock

class EnergyDrinkUpdateViewModel(
    private val id: Long,
    private val getEnergyDrinkById: GetEnergyDrinkByIdUseCase,
    private val updateEnergyDrink: UpdateEnergyDrinkUseCase
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
    }

    fun onAction(action: EnergyDrinkAddAction) {
        when (action) {
            is EnergyDrinkAddAction.OnNameChange -> updateForm {
                it.copy(name = action.name, isNameTextValid = action.name.isNotBlank())
            }
            is EnergyDrinkAddAction.OnDescriptionChange -> updateForm {
                it.copy(description = action.description)
            }
            is EnergyDrinkAddAction.OnRatingTextChange -> updateRating(action.ratingText)
            is EnergyDrinkAddAction.OnImageSelected -> {
                imageChanged = true
                updateForm { it.copy(imagePath = action.path) }
            }
            is EnergyDrinkAddAction.OnRemoveImage -> {
                imageChanged = true
                updateForm { it.copy(imagePath = null) }
            }
            is EnergyDrinkAddAction.OnSaveClick -> save()
            else -> Unit
        }
    }

    private fun loadDrink() = viewModelScope.launch {
        getEnergyDrinkById(id)
            .onSuccess { drink ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        form = EnergyDrinkAddState(
                            name = drink.name,
                            isNameTextValid = true,
                            description = drink.description.orEmpty(),
                            imagePath = drink.imagePath,
                            ratingText = drink.rating?.toString().orEmpty(),
                            isRatingTextValid = true,
                            rating = drink.rating,
                            amount = drink.amount
                        )
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        form = it.form.copy(errorMessage = error.toUiText()),
                        errorMessage = error.toUiText()
                    )
                }
            }
    }

    private fun updateForm(transform: (EnergyDrinkAddState) -> EnergyDrinkAddState) {
        _state.update { it.copy(form = transform(it.form)) }
    }

    private fun updateRating(value: String) {
        val normalized = value.replace(',', '.').trim()
        val isValid = normalized.isBlank() || (
            normalized.matches(Regex("""^(10(\.0?)?|[0-9](\.[0-9]?)?)$""") ) &&
                normalized.toDoubleOrNull()?.let { it in 0.0..10.0 } == true
            )
        updateForm {
            it.copy(
                ratingText = normalized,
                rating = normalized.toDoubleOrNull(),
                isRatingTextValid = isValid
            )
        }
    }

    private fun save() = viewModelScope.launch {
        val form = _state.value.form
        val name = form.name.trim()
        if (form.isSaving) return@launch
        if (name.isEmpty() || !form.isRatingTextValid) {
            updateForm { it.copy(isNameTextValid = name.isNotEmpty()) }
            return@launch
        }

        updateForm { it.copy(isSaving = true) }
        updateEnergyDrink(
            id = id,
            name = name,
            amount = form.amount,
            description = form.description,
            rating = form.rating,
            updatedAt = Clock.System.now().toEpochMilliseconds(),
            imagePath = form.imagePath,
            imageChanged = imageChanged
        ).onSuccess {
            updateForm { it.copy(isSaving = false, isSaved = true) }
        }.onError { error ->
            updateForm { it.copy(isSaving = false, errorMessage = error.toUiText()) }
        }
    }
}
