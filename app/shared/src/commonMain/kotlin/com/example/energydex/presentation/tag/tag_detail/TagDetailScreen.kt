package com.example.energydex.presentation.tag.tag_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.AccentRedGradientVertical
import com.example.energydex.core.presentation.ErrorRed
import com.example.energydex.core.presentation.AppBackground
import com.example.energydex.core.presentation.GreenGradientVertical
import com.example.energydex.core.presentation.AccentRedGradientVertical
import com.example.energydex.core.presentation.glassThumb
import com.example.energydex.presentation.shared.components.EnergyDrinkListContent
import com.example.energydex.presentation.shared.components.EnergyDrinkSectionTab
import com.example.energydex.presentation.shared.components.GlassBackButton
import com.example.energydex.presentation.shared.components.GlassCircleButton
import com.example.energydex.core.presentation.tagGradient
import com.kashif_e.backdrop.backdrops.layerBackdrop
import com.kashif_e.backdrop.backdrops.rememberLayerBackdrop
import energydex.app.shared.generated.resources.Res
import energydex.app.shared.generated.resources.ic_delete
import energydex.app.shared.generated.resources.ic_edit
import energydex.app.shared.generated.resources.ic_tag_edit
import energydex.app.shared.generated.resources.ic_tag

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
                is TagDetailAction.OnEnergyDrinkHold,
                is TagDetailAction.OnSelectEnergyDrink,
                TagDetailAction.OnCancelSelectionClick,
                TagDetailAction.OnRemoveSelectedClick -> Unit
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
    val backdrop = rememberLayerBackdrop()

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .layerBackdrop(backdrop)
        ) {
            if (state.currentTag == null && state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                EnergyDrinkListContent(
                    modifier = Modifier.fillMaxWidth(),
                    searchQuery = state.searchQuery,
                    searchResult = state.energyDrinks,
                    selectedDrinkIds = state.selectedEnergyDrinkIds,
                    isSelectionMode = state.isSelectionMode,
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
                        onAction(
                            if (state.isSelectionMode) {
                                TagDetailAction.OnSelectEnergyDrink(it)
                            } else {
                                TagDetailAction.OnEnergyDrinkNavigateClick(it)
                            }
                        )
                    },
                    onEnergyDrinkLongClick = {
                        onAction(TagDetailAction.OnEnergyDrinkHold(it))
                    },
                    onEnergyDrinkSelectionClick = {
                        onAction(TagDetailAction.OnSelectEnergyDrink(it))
                    },
                    onCancelSelectionClick = {
                        onAction(TagDetailAction.OnCancelSelectionClick)
                    },
                    onDeleteSelectedClick = {
                        onAction(TagDetailAction.OnRemoveSelectedClick)
                    },
                    onSortButtonClick = {
                        onAction(TagDetailAction.OnSortButtonClick)
                    },
                    onSortOptionSelected = {
                        onAction(TagDetailAction.OnSortOptionSelected(it))
                    },
                    primaryActionDescription = "Edit drinks in tag",
                    primaryActionIcon = Res.drawable.ic_edit,
                    showTagSelectionAction = false,
                    selectionActionIcon = Res.drawable.ic_tag,
                    selectionActionDescription = "Remove selected from tag",
                    selectionActionTintBrush = AccentRedGradientVertical,
                    onPrimaryActionClick = if (state.isSelectionMode) null else {
                        { onAction(TagDetailAction.OnAddDrinksClick) }
                    }
                )
            }
        }

        val titleShape = androidx.compose.foundation.shape.RoundedCornerShape(50)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 8.dp, vertical = 12.dp)
                .height(50.dp)
        ) {
            state.currentTag?.let { tag ->
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(180.dp)
                        .height(50.dp)
                        .clip(titleShape)
                        .glassThumb(
                            backdrop = backdrop,
                            shape = titleShape,
                            tintBrush = tagGradient(tag.color)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tag.name,
                        color = AppBackground,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlassBackButton(
                    backdrop = backdrop,
                    onClick = { onAction(TagDetailAction.OnBackClick) }
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    GlassCircleButton(
                        onClick = { onAction(TagDetailAction.OnEditClick) },
                        contentDescription = "Edit tag",
                        icon = Res.drawable.ic_tag_edit,
                        backdrop = backdrop,
                        size = 50.dp,
                        tintBrush = GreenGradientVertical
                    )
                    GlassCircleButton(
                        onClick = { onAction(TagDetailAction.OnDeleteClick) },
                        contentDescription = "Delete tag",
                        icon = Res.drawable.ic_delete,
                        backdrop = backdrop,
                        size = 50.dp,
                        tintBrush = AccentRedGradientVertical
                    )
                }
            }
        }
    }
}
