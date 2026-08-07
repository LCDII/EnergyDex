package com.example.energydex.presentation.energydrink.energydrink_add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.ErrorRed
import com.example.energydex.core.presentation.PrimaryOrange
import com.example.energydex.core.presentation.PrimaryPurple
import com.example.energydex.core.presentation.SecondaryOrange
import com.example.energydex.core.presentation.SecondaryPurple
import com.example.energydex.presentation.energydrink.energydrink_add.components.EnergyDrinkDescriptionTextField
import com.example.energydex.presentation.energydrink.energydrink_add.components.EnergyDrinkNameTextField
import com.example.energydex.presentation.energydrink.energydrink_add.components.EnergyDrinkRatingTextField
import energydex.composeapp.generated.resources.Res
import energydex.composeapp.generated.resources.ic_energy_drink_add
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EnergyDrinkAddAddScreenRoot(
    viewModel: EnergyDrinkAddViewModel = koinViewModel(),
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) {
            onSaveClick()
        }
    }

    EnergyDrinkAddScreen(
        state = state,
        onAction = { action ->
            if (action is EnergyDrinkAddAction.OnBackClick) {
                onBackClick()
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
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { onAction(EnergyDrinkAddAction.OnBackClick) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = SecondaryPurple,
                    contentColor = AccentWhite
                )
            ) {
                Text("Back")
            }

            Text(
                text = "Add energy drink",
                color = AccentWhite,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.width(72.dp))
        }

        EnergyDrinkNameTextField(
            modifier = Modifier.fillMaxWidth(),
            name = state.name,
            onNameChange = { onAction(EnergyDrinkAddAction.OnNameChange(it)) },
            isValid = state.isNameTextValid
        )

        EnergyDrinkDescriptionTextField(
            modifier = Modifier.fillMaxWidth(),
            description = state.description,
            onDescriptionChange = {
                onAction(EnergyDrinkAddAction.OnDescriptionChange(it))
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Rating from 0 to 10",
                color = SecondaryOrange,
                fontSize = 16.sp
            )

            EnergyDrinkRatingTextField(
                ratingText = state.ratingText,
                onRatingChange = {
                    onAction(EnergyDrinkAddAction.OnRatingTextChange(it))
                },
                isValid = state.isRatingTextValid
            )
        }

        state.errorMessage?.let { errorMessage ->
            Text(
                text = errorMessage.asString(),
                color = ErrorRed,
                fontSize = 14.sp
            )
        }

        FloatingActionButton(
            onClick = {
                if (!state.isSaving) {
                    onAction(EnergyDrinkAddAction.OnSaveClick)
                }
            },
            elevation = FloatingActionButtonDefaults.elevation(4.dp),
            containerColor = PrimaryOrange,
            modifier = Modifier
                .align(Alignment.End)
                .size(56.dp),
            shape = CircleShape
        ) {
            if (state.isSaving) {
                Text("...", color = AccentWhite)
            } else {
                Icon(
                    painter = painterResource(Res.drawable.ic_energy_drink_add),
                    contentDescription = "Save",
                    tint = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
