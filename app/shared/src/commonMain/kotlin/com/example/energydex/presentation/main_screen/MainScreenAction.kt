package com.example.energydex.presentation.main_screen

import com.example.energydex.presentation.main_screen.components.MainScreenTab

sealed interface MainScreenAction {
    data class OnTabSelected(val index: MainScreenTab): MainScreenAction
}