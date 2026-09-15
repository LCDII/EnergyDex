package dev.lcdii.energydex.presentation.energydrink.energydrink_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.lcdii.energydex.core.domain.onError
import dev.lcdii.energydex.core.domain.onSuccess
import dev.lcdii.energydex.core.presentation.toUiText
import dev.lcdii.energydex.domain.energydrink.usecase.GetEnergyDrinkByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EnergyDrinkDetailViewModel(
    private val id: Long,
    private val getEnergyDrinkById: GetEnergyDrinkByIdUseCase
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
            EnergyDrinkDetailAction.OnBackClick->{

            }
            EnergyDrinkDetailAction.OnUpdateClick ->{

            }
        }
    }
}
