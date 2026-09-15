package dev.lcdii.energydex.presentation.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MainScreenViewModel(

): ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state = _state.
            stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000L),
                _state.value
            )

    fun onAction(action: MainScreenAction){
        when(action){
            is MainScreenAction.OnTabSelected -> {
                _state.update { it.copy(
                    selectedTabIndex = action.index
                ) }
            }
        }
    }
}