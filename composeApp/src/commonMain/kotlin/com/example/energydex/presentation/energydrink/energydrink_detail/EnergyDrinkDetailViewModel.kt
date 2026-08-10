package com.example.energydex.presentation.energydrink.energydrink_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.energydex.core.domain.onError
import com.example.energydex.core.domain.onSuccess
import com.example.energydex.core.presentation.toUiText
import com.example.energydex.domain.energydrink.usecase.DeleteEnergyDrinkUseCase
import com.example.energydex.domain.energydrink.usecase.GetEnergyDrinkByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EnergyDrinkDetailViewModel(
    private val id: Long,
    private val getEnergyDrinkById: GetEnergyDrinkByIdUseCase,
    private val deleteEnergyDrink: DeleteEnergyDrinkUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(EnergyDrinkDetailState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    init {
        loadDrink()
    }

    private fun loadDrink() = viewModelScope.launch {
        getEnergyDrinkById(id)
            .onSuccess { drink ->
                _state.update {
                    it.copy(isLoading = false, currentDrink = drink, errorMessage = null)
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(isLoading = false, errorMessage = error.toUiText())
                }
            }
    }

    fun onAction(action: EnergyDrinkDetailAction) {
        when (action) {
            EnergyDrinkDetailAction.OnDeleteClick -> {
                _state.update { it.copy(showDeleteConfirmation = true) }
            }
            EnergyDrinkDetailAction.OnConfirmDeleteClick -> {
                viewModelScope.launch {
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
            EnergyDrinkDetailAction.OnDeclineDeleteClick -> {
                _state.update { it.copy(showDeleteConfirmation = false) }
            }
            EnergyDrinkDetailAction.OnBackClick->{

            }
            EnergyDrinkDetailAction.OnUpdateClick ->{

            }
        }
    }
}
