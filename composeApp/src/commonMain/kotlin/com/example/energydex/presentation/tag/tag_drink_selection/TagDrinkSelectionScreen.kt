package com.example.energydex.presentation.tag.tag_drink_selection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.ErrorRed
import com.example.energydex.core.presentation.PrimaryPurple
import com.example.energydex.core.presentation.SecondaryOrange
import com.example.energydex.core.presentation.SecondaryPurple
import com.example.energydex.presentation.shared.components.EnergyDrinkListContent

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryPurple)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(
                    containerColor = SecondaryPurple,
                    contentColor = AccentWhite
                )
            ) {
                Text("Back")
            }
            Text(
                text = state.currentTag?.name ?: "Tag",
                color = AccentWhite,
                fontSize = 22.sp,
                modifier = Modifier.weight(1f)
            )
            TextButton(
                onClick = { onAction(TagDrinkSelectionAction.OnSaveClick) },
                enabled = !state.isSaving
            ) {
                Text(
                    text = if (state.isSaving) "Saving..." else "Save",
                    color = SecondaryOrange
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            if (state.currentTag == null && state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage.asString(),
                    color = ErrorRed,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                EnergyDrinkListContent(
                    searchQuery = state.searchQuery,
                    searchResult = state.energyDrinks,
                    selectedTab = state.selectedTab,
                    isLoading = state.isLoading,
                    errorMessage = state.errorMessage,
                    selectedDrinkIds = state.selectedDrinkIds,
                    sortOption = state.sortOption,
                    isSortMenuVisible = state.isSortMenuVisible,
                    onSearchQueryChange = {
                        onAction(TagDrinkSelectionAction.OnSearchQueryChange(it))
                    },
                    onTabSelected = {
                        onAction(TagDrinkSelectionAction.OnTabSelected(it))
                    },
                    onEnergyDrinkClick = {
                        onAction(TagDrinkSelectionAction.OnDrinkToggle(it.id))
                    },
                    onSortButtonClick = {
                        onAction(TagDrinkSelectionAction.OnSortButtonClick)
                    },
                    onSortOptionSelected = {
                        onAction(TagDrinkSelectionAction.OnSortOptionSelected(it))
                    }
                )
            }
        }
    }
}
