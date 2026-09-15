package dev.lcdii.energydex.presentation.main_screen

import dev.lcdii.energydex.core.presentation.UiText
import dev.lcdii.energydex.presentation.main_screen.components.MainScreenTab

data class MainScreenState(
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val selectedTabIndex: MainScreenTab = MainScreenTab.ENERGY_DRINKS
)