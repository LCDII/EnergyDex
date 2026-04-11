package com.example.energydeks.presentation.energydrink.energydrink_section.previews

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.energydeks.presentation.energydrink.energydrink_section.EnergyDrinkSection
import com.example.energydeks.presentation.energydrink.energydrink_section.EnergyDrinkSectionState

@Preview
@Composable
fun EnergyDrinkSectionPreview() {
    //Calling the stateless EnergyDrinkSection instead of EnergyDrinkSectionRoot
    // to avoid the "KoinApplication has not been started" error in the preview.
    MaterialTheme {
        EnergyDrinkSection(
            state = EnergyDrinkSectionState(
                isLoading = false
            ),
            onAction = {}
        )
    }
}