package com.example.energydex.presentation.tag.tag_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.ErrorRed
import com.example.energydex.core.presentation.PrimaryPurple
import com.example.energydex.core.presentation.SecondaryOrange
import com.example.energydex.core.presentation.SecondaryPurple
import com.example.energydex.presentation.shared.components.EnergyDrinkItemLonged
import com.example.energydex.presentation.shared.components.EnergyDrinkItemSquared
import com.example.energydex.presentation.shared.components.EnergyDrinkSearchBar
import com.example.energydex.presentation.shared.components.EnergyDrinkSectionTab
import com.example.energydex.presentation.shared.components.EnergyDrinkViewModeToggle

@Composable
fun TagDetailScreenRoot(
    viewModel: TagDetailViewModel,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onAddDrinksClick: () -> Unit,
    onDrinkClick: (Long) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(EnergyDrinkSectionTab.LONGED) }

    LaunchedEffect(state.isDeleted) {
        if (state.isDeleted) onBackClick()
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete tag?") },
            text = { Text("Drinks will not be deleted.") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    viewModel.onAction(TagDetailAction.OnConfirmDeleteClick)
                }) { Text("Delete", color = ErrorRed) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Cancel") }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryPurple)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onBackClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = SecondaryPurple,
                    contentColor = AccentWhite
                )
            ) { Text("Back") }
            TextButton(onClick = onEditClick) {
                Text("Edit", color = SecondaryOrange)
            }
        }

        state.tag?.let { tag ->
            Text(tag.name, color = AccentWhite, fontSize = 28.sp)
            Text("${state.drinks.size} drinks", color = SecondaryOrange)
            Button(
                onClick = onAddDrinksClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SecondaryPurple,
                    contentColor = AccentWhite
                )
            ) { Text("Add drinks to tag") }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                EnergyDrinkSearchBar(
                    searchQuery = searchQuery,
                    onSearchQueryChange = { searchQuery = it },
                    onImeSearch = {},
                    modifier = Modifier.weight(3f).fillMaxWidth()
                )
                EnergyDrinkViewModeToggle(
                    modifier = Modifier.weight(1f),
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            }
            val filteredDrinks = state.drinks.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }
            if (selectedTab == EnergyDrinkSectionTab.LONGED) {
                filteredDrinks.forEach { drink ->
                        EnergyDrinkItemLonged(
                            energyDrink = drink,
                            onClick = { onDrinkClick(drink.id) },
                            modifier = Modifier.fillMaxWidth()
                        )
                }
            } else {
                FlowRow(
                    maxItemsInEachRow = 2,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    filteredDrinks.forEach { drink ->
                        EnergyDrinkItemSquared(
                            energyDrink = drink,
                            onClick = { onDrinkClick(drink.id) },
                            modifier = Modifier.width(170.dp)
                        )
                    }
                }
            }
            Button(
                onClick = { showDeleteDialog = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ErrorRed,
                    contentColor = AccentWhite
                )
            ) { Text("Delete tag") }
        } ?: state.errorMessage?.let { error ->
            Text(error.asString(), color = ErrorRed)
        }
    }
}
