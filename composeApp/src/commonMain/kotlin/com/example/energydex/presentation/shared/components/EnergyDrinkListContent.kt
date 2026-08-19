package com.example.energydex.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.GlassButtonGradient
import com.example.energydex.core.presentation.GlassChipTint
import com.example.energydex.core.presentation.PrimaryOrange
import com.example.energydex.core.presentation.PrimaryPurple
import com.example.energydex.core.presentation.UiText
import com.example.energydex.core.presentation.glassBackdrop
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.domain.tag.model.Tag
import com.example.energydex.presentation.energydrink.energydrink_section.components.EnergyDrinkListSortOptions
import com.kashif_e.backdrop.Backdrop
import com.kashif_e.backdrop.backdrops.layerBackdrop
import com.kashif_e.backdrop.backdrops.rememberCanvasBackdrop
import com.kashif_e.backdrop.backdrops.rememberLayerBackdrop
import energydex.composeapp.generated.resources.Res
import energydex.composeapp.generated.resources.ic_energy_drink_add
import energydex.composeapp.generated.resources.ic_energy_drinks_filter
import energydex.composeapp.generated.resources.no_search_results
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private val SearchRowTopPadding = 84.dp
private val ListContentTopPadding = 166.dp

@Composable
fun EnergyDrinkListContent(
    modifier: Modifier = Modifier,
    searchQuery: String,
    searchResult: List<EnergyDrink>,
    selectedTab: EnergyDrinkSectionTab,
    isLoading: Boolean,
    errorMessage: UiText?,
    selectedDrinkIds: Set<Long> = emptySet(),
    isSelectionMode: Boolean = false,
    isDeleteDialogVisible: Boolean = false,
    isTagDialogVisible: Boolean = false,
    tags: List<Tag> = emptyList(),
    selectedTagIds: Set<Long> = emptySet(),
    isBulkOperationRunning: Boolean = false,
    sortOption: EnergyDrinkListSortOptions,
    isSortMenuVisible: Boolean,
    onSearchQueryChange: (String) -> Unit,
    onTabSelected: (EnergyDrinkSectionTab) -> Unit,
    onEnergyDrinkClick: (EnergyDrink) -> Unit,
    onEnergyDrinkLongClick: (EnergyDrink) -> Unit = {},
    onEnergyDrinkSelectionClick: (EnergyDrink) -> Unit = {},
    onCancelSelectionClick: () -> Unit = {},
    onDeleteSelectedClick: () -> Unit = {},
    onConfirmDeleteSelectedClick: () -> Unit = {},
    onDismissDeleteDialogClick: () -> Unit = {},
    onTagSelectedClick: () -> Unit = {},
    onToggleTag: (Long) -> Unit = {},
    onConfirmTagSelectedClick: () -> Unit = {},
    onDismissTagDialogClick: () -> Unit = {},
    onCreateTagClick: () -> Unit = {},
    onSortButtonClick: () -> Unit,
    onSortOptionSelected: (EnergyDrinkListSortOptions) -> Unit,
    onPrimaryActionClick: (() -> Unit)? = null,
    primaryActionDescription: String = ""
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val listGlassState = rememberLayerBackdrop()

    val cardBackdrop = rememberCanvasBackdrop {
        drawRect(PrimaryPurple)
    }

    Box(modifier = modifier.fillMaxSize()) {
        when {
            isLoading -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
            errorMessage != null -> Text(
                text = errorMessage.asString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                color = PrimaryOrange,
                modifier = Modifier.align(Alignment.Center)
            )
            searchResult.isEmpty() -> Text(
                text = stringResource(Res.string.no_search_results),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                color = PrimaryOrange,
                modifier = Modifier.align(Alignment.Center)
            )
            else -> when (selectedTab) {
                EnergyDrinkSectionTab.SQUARED -> {
                    val scrollState = rememberLazyGridState()
                    LaunchedEffect(searchResult) {
                        scrollState.animateScrollToItem(0)
                    }
                    EnergyDrinkListSquared(
                        energyDrinks = searchResult,
                        onEnergyDrinkClick = onEnergyDrinkClick,
                        onEnergyDrinkLongClick = onEnergyDrinkLongClick,
                        onEnergyDrinkSelectionClick = onEnergyDrinkSelectionClick,
                        isSelectionMode = isSelectionMode,
                        scrollState = scrollState,
                        selectedDrinkIds = selectedDrinkIds,
                        modifier = Modifier
                            .fillMaxSize()
                            .layerBackdrop(listGlassState),
                        cardBackdrop = cardBackdrop,
                        contentPadding = PaddingValues(
                            start = 8.dp,
                            end = 8.dp,
                            top = ListContentTopPadding,
                            bottom = 8.dp
                        )
                    )
                }
                else -> {
                    val scrollState = rememberLazyListState()
                    LaunchedEffect(searchResult) {
                        scrollState.animateScrollToItem(0)
                    }
                    EnergyDrinkListLonged(
                        energyDrinks = searchResult,
                        onEnergyDrinkClick = onEnergyDrinkClick,
                        onEnergyDrinkLongClick = onEnergyDrinkLongClick,
                        onEnergyDrinkSelectionClick = onEnergyDrinkSelectionClick,
                        isSelectionMode = isSelectionMode,
                        scrollState = scrollState,
                        selectedDrinkIds = selectedDrinkIds,
                        modifier = Modifier
                            .fillMaxSize()
                            .layerBackdrop(listGlassState),
                        cardBackdrop = cardBackdrop,
                        contentPadding = PaddingValues(
                            start = 8.dp,
                            end = 8.dp,
                            top = ListContentTopPadding,
                            bottom = 8.dp
                        )
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(start = 16.dp, end = 16.dp, top = SearchRowTopPadding, bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            EnergyDrinkSearchBar(
                modifier = Modifier
                    .weight(1f)
                    .widthIn(max = 520.dp)
                    .glassBackdrop(
                        backdrop = listGlassState,
                        shape = RoundedCornerShape(100),
                        tint = GlassChipTint
                    ),
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                onImeSearch = { keyboardController?.hide() }
            )
            EnergyDrinkViewModeToggle(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected,
                modifier = Modifier.glassBackdrop(
                    backdrop = listGlassState,
                    shape = RoundedCornerShape(12.dp),
                    tint = GlassChipTint
                )
            )
            Box {
                GlassCircleButton(
                    onClick = onSortButtonClick,
                    contentDescription = "Sort",
                    icon = Res.drawable.ic_energy_drinks_filter,
backdrop = listGlassState,
                )

                DropdownMenu(
                    expanded = isSortMenuVisible,
                    onDismissRequest = onSortButtonClick
                ) {
                    listOf(
                        EnergyDrinkListSortOptions.TITLE_ASC,
                        EnergyDrinkListSortOptions.DATE_ASC,
                        EnergyDrinkListSortOptions.RATING_ASC
                    ).forEach { option ->
                        val isCurrent = option.fieldGroup() == sortOption.fieldGroup()
                        DropdownMenuItem(
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = option.label(),
                                        color = if (isCurrent) PrimaryOrange else Color.Unspecified
                                    )
                                    if (isCurrent) {
                                        Text(
                                            text = if (sortOption.isAscending()) "↓" else "↑",
                                            color = PrimaryOrange
                                        )
                                    }
                                }
                            },
                            onClick = { onSortOptionSelected(option) }
                        )
                    }
                }
            }
        }

        if (isSelectionMode) {
            EnergyDrinkSelectionToolbar(
                selectedCount = selectedDrinkIds.size,
                isBusy = isBulkOperationRunning,
                onDeleteClick = onDeleteSelectedClick,
                onTagClick = onTagSelectedClick,
                onCancelClick = onCancelSelectionClick,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }

        onPrimaryActionClick?.let { onClick ->
            GlassCircleButton(
                onClick = onClick,
                contentDescription = primaryActionDescription,
                icon = Res.drawable.ic_energy_drink_add,
                backdrop = listGlassState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
            )
        }

        if (isDeleteDialogVisible) {
            AlertDialog(
                onDismissRequest = onDismissDeleteDialogClick,
                title = { Text("Delete drinks?") },
                text = { Text("Delete ${selectedDrinkIds.size} selected drinks?") },
                confirmButton = {
                    Button(onClick = onConfirmDeleteSelectedClick) { Text("Delete") }
                },
                dismissButton = {
                    TextButton(onClick = onDismissDeleteDialogClick) { Text("Cancel") }
                }
            )
        }

        if (isTagDialogVisible) {
            AlertDialog(
                onDismissRequest = onDismissTagDialogClick,
                title = { Text("Add tags") },
                text = {
                    Column {
                        TextButton(onClick = onCreateTagClick) {
                            Text("Create new tag")
                        }
                        if (tags.isEmpty()) {
                            Text("No tags available")
                        } else {
                            tags.forEach { tag ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(
                                        checked = tag.id in selectedTagIds,
                                        onCheckedChange = { onToggleTag(tag.id) }
                                    )
                                    Text(tag.name)
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = onConfirmTagSelectedClick,
                        enabled = selectedTagIds.isNotEmpty() && !isBulkOperationRunning
                    ) { Text("Add") }
                },
                dismissButton = {
                    TextButton(onClick = onDismissTagDialogClick) { Text("Cancel") }
                }
            )
        }
    }
}

@Composable
private fun EnergyDrinkSelectionToolbar(
    selectedCount: Int,
    isBusy: Boolean,
    onDeleteClick: () -> Unit,
    onTagClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(PrimaryPurple)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextButton(onClick = onCancelClick, enabled = !isBusy) {
            Text("Cancel", color = AccentWhite)
        }
        Text(
            text = "$selectedCount selected",
            color = AccentWhite,
            modifier = Modifier.weight(1f)
        )
        TextButton(onClick = onTagClick, enabled = !isBusy) {
            Text("Tag", color = AccentWhite)
        }
        TextButton(onClick = onDeleteClick, enabled = !isBusy) {
            Text("Delete", color = PrimaryOrange)
        }
    }
}

@Composable
private fun GlassCircleButton(
    onClick: () -> Unit,
    contentDescription: String,
    icon: DrawableResource,
    backdrop: Backdrop,
    modifier: Modifier = Modifier
) {
    val circle = CircleShape
    Box(
        modifier = modifier
            .size(58.dp)
            .glassBackdrop(
                backdrop = backdrop,
                shape = circle,
                tintBrush = GlassButtonGradient,
                tintOpacity = 0.85f,
                blurRadius = 18.dp,
                brightness = 0.05f,
                contrast = 1.1f,
                saturation = 1.3f
            ),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(icon),
                contentDescription = contentDescription,
                tint = AccentWhite,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}

private fun EnergyDrinkListSortOptions.fieldGroup(): String = name.substringBefore("_")

private fun EnergyDrinkListSortOptions.isAscending(): Boolean = name.endsWith("_ASC")

private fun EnergyDrinkListSortOptions.label(): String = when (this) {
    EnergyDrinkListSortOptions.TITLE_ASC,
    EnergyDrinkListSortOptions.TITLE_DESC -> "Title"
    EnergyDrinkListSortOptions.DATE_ASC,
    EnergyDrinkListSortOptions.DATE_DESC -> "Date"
    EnergyDrinkListSortOptions.RATING_ASC,
    EnergyDrinkListSortOptions.RATING_DESC -> "Rating"
}
