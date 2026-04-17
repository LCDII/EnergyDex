package com.example.energydex.presentation.energydrink.energydrink_add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import com.example.energydex.core.presentation.PrimaryPurple

@Composable
fun EnergyDrinkAddAddScreenRoot(
    viewModel: EnergyDrinkAddViewModel = koinViewModel(),
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    EnergyDrinkAddScreen(
        state,
        onAction = { action ->
            when(action) {
                is EnergyDrinkAddAction.OnBackClick -> {
                    onBackClick()
                }
                is EnergyDrinkAddAction.OnSaveClick -> {
                    onSaveClick()
                }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}


@Composable
fun EnergyDrinkAddScreen(
    state: EnergyDrinkAddState,
    onAction: (EnergyDrinkAddAction) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryPurple)
            .statusBarsPadding(),
    ){

    }
}