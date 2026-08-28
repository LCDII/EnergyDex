package com.example.energydex.presentation.tag.tag_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.AlertDialog
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
import com.example.energydex.core.presentation.AppBackground
import com.example.energydex.core.presentation.SecondaryPurple
import com.example.energydex.presentation.shared.components.EnergyDrinkListContent
import com.example.energydex.presentation.shared.components.EnergyDrinkSectionTab

@Composable
fun TagDetailScreenRoot(
    viewModel: TagDetailViewModel,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onAddDrinksClick: () -> Unit,
    onDrinkClick: (Long) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isDeleted) {
        if (state.isDeleted) onBackClick()
    }

    TagDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                TagDetailAction.OnBackClick -> onBackClick()
                TagDetailAction.OnEditClick -> onEditClick()
                TagDetailAction.OnAddDrinksClick -> onAddDrinksClick()
                is TagDetailAction.OnEnergyDrinkNavigateClick -> {
                    onDrinkClick(action.energyDrink.id)
                }
                TagDetailAction.OnDeleteClick,
                TagDetailAction.OnConfirmDeleteClick,
                TagDetailAction.OnDeclineDeleteClick -> Unit
                is TagDetailAction.OnSearchQueryChange,
                is TagDetailAction.OnTabSelected,
                TagDetailAction.OnSortButtonClick,
                is TagDetailAction.OnSortOptionSelected -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun TagDetailScreen(
    state: TagDetailState,
    onAction: (TagDetailAction) -> Unit
) {
    if (state.isDeleteDialogVisible) {
        AlertDialog(
            onDismissRequest = {
                onAction(TagDetailAction.OnDeclineDeleteClick)
            },
            title = { Text("Delete tag?") },
            text = { Text("Drinks will not be deleted.") },
            confirmButton = {
                TextButton(
                    onClick = { onAction(TagDetailAction.OnConfirmDeleteClick) }
                ) {
                    Text("Delete", color = ErrorRed)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onAction(TagDetailAction.OnDeclineDeleteClick) }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { onAction(TagDetailAction.OnBackClick) },
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
            TextButton(onClick = { onAction(TagDetailAction.OnEditClick) }) {
                Text("Edit", color = AccentWhite)
            }
            TextButton(onClick = { onAction(TagDetailAction.OnDeleteClick) }) {
                Text("Delete", color = ErrorRed)
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            if (state.currentTag == null && state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                EnergyDrinkListContent(
                    modifier = Modifier.fillMaxWidth(),
                    searchQuery = state.searchQuery,
                    searchResult = state.energyDrinks,
                    selectedTab = state.selectedTabIndex,
                    isLoading = state.isLoading,
                    errorMessage = state.errorMessage,
                    sortOption = state.sortOption,
                    isSortMenuVisible = state.isSortMenuVisible,
                    onSearchQueryChange = {
                        onAction(TagDetailAction.OnSearchQueryChange(it))
                    },
                    onTabSelected = {
                        onAction(TagDetailAction.OnTabSelected(it))
                    },
                    onEnergyDrinkClick = {
                        onAction(TagDetailAction.OnEnergyDrinkNavigateClick(it))
                    },
                    onSortButtonClick = {
                        onAction(TagDetailAction.OnSortButtonClick)
                    },
                    onSortOptionSelected = {
                        onAction(TagDetailAction.OnSortOptionSelected(it))
                    },
                    onPrimaryActionClick = {
                        onAction(TagDetailAction.OnAddDrinksClick)
                    },
                    primaryActionDescription = "Add drinks to tag"
                )
            }
        }
    }
}
