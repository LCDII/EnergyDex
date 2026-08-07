package com.example.energydex.presentation.energydrink.energydrink_add


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.energydex.core.domain.onError
import com.example.energydex.core.domain.onSuccess
import com.example.energydex.core.presentation.toUiText
import com.example.energydex.domain.energydrink.usecase.CreateEnergyDrinkUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EnergyDrinkAddViewModel(
    private val createEnergyDrinkUseCase: CreateEnergyDrinkUseCase
): ViewModel() {

    private val _state = MutableStateFlow(EnergyDrinkAddState())
    val state =  _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    fun onAction(action: EnergyDrinkAddAction){
        when(action){
            is EnergyDrinkAddAction.OnBackClick -> {
                //navigation only; ui responsible for nav;
            }
            is EnergyDrinkAddAction.OnSaveClick -> {
                //navigation; ui responsible for nav;
                save()
            }
            is EnergyDrinkAddAction.OnNameChange -> {
                _state.update { it.copy(
                    name = action.name,
                    isNameTextValid = action.name.isNotBlank(),
                    errorMessage = null
                ) }
            }
            is EnergyDrinkAddAction.OnDescriptionChange -> {
                _state.update { it.copy(
                    description = action.description,
                    errorMessage = null
                ) }
            }
            is EnergyDrinkAddAction.OnRatingTextChange -> {
                updateRating(action.ratingText)
            }
            is EnergyDrinkAddAction.OnPickImage -> {
                //TODO??
            }
            is EnergyDrinkAddAction.OnImageSelected -> {
                _state.update { it.copy(
                    imagePath = action.path
                ) }
            }
            else -> Unit
        }
    }

    private fun updateRating(ratingText: String) {
        val normalizedRatingText = ratingText.replace(',', '.').trim()

        if(normalizedRatingText.isBlank())
        {
            _state.update { it.copy(
                ratingText = normalizedRatingText,
                rating = null,
                isRatingTextValid = true
            ) }
        } else {
            val ratingRegex = Regex("""^(10(\.0?)?|[0-9](\.[0-9]?)?)$""")
            val isValid = when {
                !ratingRegex.matches(normalizedRatingText) -> false
                else -> {
                    val parsed = normalizedRatingText.toDoubleOrNull()
                    parsed != null && parsed in 0.0..10.0
                }
            }

            _state.update {
                it.copy(
                    ratingText = normalizedRatingText,
                    rating = if (isValid) normalizedRatingText.toDouble() else null,
                    isRatingTextValid = isValid
                )
            }
        }
    }

    private fun save() = viewModelScope.launch {
        val currentState = _state.value
        val name = currentState.name.trim()

        if (currentState.isSaving) {
            return@launch
        }

        val isNameValid = name.isNotEmpty()
        if (!isNameValid || !currentState.isRatingTextValid) {
            _state.update {
                it.copy(
                    isNameTextValid = isNameValid,
                    errorMessage = null
                )
            }
            return@launch
        }

        _state.update { it.copy(
            isSaving = true
        ) }

        createEnergyDrinkUseCase.invoke(
            name = name,
            amount = currentState.amount,
            description = currentState.description,
            rating = currentState.rating,
            imagePath = currentState.imagePath
        )
            .onSuccess {
                _state.update { it.copy(
                    isSaving = false,
                    isSaved = true
                ) }
            }
            .onError { error->
                _state.update { it.copy(
                    isSaving = false,
                    errorMessage = error.toUiText()
                ) }
            }

    }

}
