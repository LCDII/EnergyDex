package com.example.energydeks.presentation.main_screen

import com.example.energydeks.core.presentation.UiText
import com.example.energydeks.presentation.main_screen.components.MainScreenTab

data class MainScreenState(
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val selectedTabIndex: MainScreenTab = MainScreenTab.ENERGY_DRINKS
)