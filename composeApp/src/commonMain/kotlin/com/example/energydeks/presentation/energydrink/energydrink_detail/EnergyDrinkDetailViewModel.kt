package com.example.energydeks.presentation.energydrink.energydrink_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.energydeks.core.domain.onError
import com.example.energydeks.core.domain.onSuccess
import com.example.energydeks.core.presentation.UiText
import com.example.energydeks.domain.energydrink.usecase.DeleteEnergyDrinkUseCase
import com.example.energydeks.domain.energydrink.usecase.GetEnergyDrinkByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.onFailure

class EnergyDrinkDetailViewModel(
    private val deleteEnergyDrink: DeleteEnergyDrinkUseCase,
): ViewModel() {
    private val _state = MutableStateFlow(EnergyDrinkDetailState())

    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: EnergyDrinkDetailAction){
        when(action){
            is EnergyDrinkDetailAction.OnDeleteClick->{
                _state.update { it.copy(
                    showDeleteConfirmation = true
                ) }
            }
            is EnergyDrinkDetailAction.OnConfirmDeleteClick->{
                viewModelScope.launch {
                    val result = deleteEnergyDrink.invoke(_state.value.currentDrink!!.id)
                    result.onSuccess {
                        _state.update { it.copy(
                            showDeleteConfirmation = false
                        ) }
                        //navigate back to main screen
                    }.onError { error ->
                        _state.update {
                            it.copy(
                                showDeleteConfirmation = false,
                                errorMessage = UiText.DynamicString(error.name)
                            )
                        }
                    }
                }
            }
            is EnergyDrinkDetailAction.OnDeclineDeleteClick->{
                _state.update { it.copy(
                    showDeleteConfirmation = false
                ) }
            }
            is EnergyDrinkDetailAction.OnBackClick->{
                //navigate back
            }
            is EnergyDrinkDetailAction.OnUpdateClick->{
                //navigate to update
            }
        }
    }
}