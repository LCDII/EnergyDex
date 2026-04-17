package com.example.energydex.presentation.energydrink.energydrink_add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.PrimaryOrange
import org.koin.compose.viewmodel.koinViewModel
import com.example.energydex.core.presentation.PrimaryPurple
import com.example.energydex.presentation.energydrink.energydrink_add.components.EnergyDrinkDescriptionTextField
import com.example.energydex.presentation.energydrink.energydrink_add.components.EnergyDrinkNameTextField
import energydex.composeapp.generated.resources.Res
import energydex.composeapp.generated.resources.ic_energy_drink_add
import energydex.composeapp.generated.resources.ic_energy_drinks_filter
import org.jetbrains.compose.resources.painterResource

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


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryPurple)
            .statusBarsPadding(),
    ){
        EnergyDrinkNameTextField(
            name = state.name,
            onNameChange = {
                onAction(EnergyDrinkAddAction.OnNameChange(it))
            },
            isValid = state.isNameTextValid
        )

        EnergyDrinkDescriptionTextField(
            description = state.description,
            onDescriptionChange = {
                onAction(EnergyDrinkAddAction.OnDescriptionChange(it))
            }
        )

        FloatingActionButton(
            onClick = {
                onAction(EnergyDrinkAddAction.OnSaveClick)
            },
            elevation = FloatingActionButtonDefaults.elevation(4.dp),
            containerColor = Color.Transparent,
            modifier = Modifier.size(56.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        PrimaryOrange,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_energy_drink_add),
                    contentDescription = "Create",
                    tint = AccentWhite,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}