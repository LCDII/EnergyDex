package com.example.energydex.presentation.tag.tag_drink_selection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.ErrorRed
import com.example.energydex.core.presentation.PrimaryOrange
import com.example.energydex.core.presentation.PrimaryPurple
import com.example.energydex.core.presentation.SecondaryOrange
import com.example.energydex.core.presentation.SecondaryPurple
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.presentation.shared.components.EnergyDrinkItemLonged
import com.example.energydex.presentation.shared.components.EnergyDrinkListLonged
import com.example.energydex.presentation.shared.components.EnergyDrinkListSquared
import com.example.energydex.presentation.shared.components.EnergyDrinkSearchBar
import com.example.energydex.presentation.shared.components.EnergyDrinkSectionTab
import com.example.energydex.presentation.shared.components.EnergyDrinkViewModeToggle

@Composable
fun TagDrinkSelectionScreenRoot(
    viewModel: TagDrinkSelectionViewModel,
    onSaved: () -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(state.isSaved) {
        if (state.isSaved) onSaved()
    }

    TagDrinkSelectionScreen(
        state = state,
        onBack = onBack,
        onAction = viewModel::onAction
    )
}

@Composable
private fun TagDrinkSelectionScreen(
    state: TagDrinkSelectionState,
    onBack: () -> Unit,
    onAction: (TagDrinkSelectionAction) -> Unit
) {
    val filteredDrinks = state.drinks.filter {
        state.searchQuery.isBlank() || it.name.contains(state.searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryPurple)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(
                    containerColor = SecondaryPurple,
                    contentColor = AccentWhite
                )
            ) { Text("Back") }
            Text("Drinks", color = AccentWhite, fontSize = 24.sp)
            TextButton(onClick = { onAction(TagDrinkSelectionAction.OnSaveClick) }) {
                Text(if (state.isSaving) "Saving..." else "Save", color = SecondaryOrange)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            EnergyDrinkSearchBar(
                searchQuery = state.searchQuery,
                onSearchQueryChange = {
                    onAction(TagDrinkSelectionAction.OnSearchQueryChange(it))
                },
                onImeSearch = {},
                modifier = Modifier.weight(3f).fillMaxWidth()
            )
            EnergyDrinkViewModeToggle(
                modifier = Modifier.weight(1f),
                selectedTab = state.selectedTab,
                onTabSelected = {
                    onAction(TagDrinkSelectionAction.OnTabSelected(it))
                }
            )
        }

        if (state.errorMessage != null) {
            Text(state.errorMessage.asString(), color = ErrorRed)
        } else if (state.selectedTab == EnergyDrinkSectionTab.SQUARED) {
            EnergyDrinkListSquared(
                energyDrinks = filteredDrinks,
                onEnergyDrinkClick = {
                    onAction(TagDrinkSelectionAction.OnDrinkToggle(it.id))
                },
                modifier = Modifier.fillMaxSize(),
                selectedDrinkIds = state.selectedDrinkIds
            )
        } else {
            EnergyDrinkListLonged(
                energyDrinks = filteredDrinks,
                onEnergyDrinkClick = {
                    onAction(TagDrinkSelectionAction.OnDrinkToggle(it.id))
                },
                modifier = Modifier.fillMaxSize(),
                selectedDrinkIds = state.selectedDrinkIds
            )
        }
    }
}
