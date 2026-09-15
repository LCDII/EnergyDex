package dev.lcdii.energydex.presentation.main_screen

import dev.lcdii.energydex.presentation.main_screen.components.MainScreenTab

sealed interface MainScreenAction {
    data class OnTabSelected(val index: MainScreenTab): MainScreenAction
}