package com.example.energydex.presentation.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.energydex.core.presentation.AppBackground
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.GlassPanelTint
import com.example.energydex.core.presentation.PrimaryOrange
import com.example.energydex.core.presentation.UiText
import com.example.energydex.core.presentation.glassContainer
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.domain.tag.model.Tag
import com.example.energydex.presentation.energydrink.energydrink_section.components.EnergyDrinkListSortOptions
import com.kashif_e.backdrop.backdrops.layerBackdrop
import com.kashif_e.backdrop.backdrops.rememberCanvasBackdrop
import com.kashif_e.backdrop.backdrops.rememberLayerBackdrop
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import com.example.energydex.core.presentation.AccentRedGradientVertical
import com.example.energydex.core.presentation.TagPinkColor
import com.example.energydex.core.presentation.GreenGradientVertical
import com.example.energydex.core.presentation.TextOnBackground
import energydex.composeapp.generated.resources.Res
import energydex.composeapp.generated.resources.ic_close
import energydex.composeapp.generated.resources.ic_delete
import energydex.composeapp.generated.resources.ic_energy_drink_add
import energydex.composeapp.generated.resources.ic_energy_drinks_filter
import energydex.composeapp.generated.resources.ic_tag
import energydex.composeapp.generated.resources.no_search_results
import org.jetbrains.compose.resources.stringResource

private val SearchRowTopPadding = 84.dp
private val ListContentTopPadding = 166.dp
private val SortPanelTopPadding = SearchRowTopPadding + 50.dp + 8.dp

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
    isDrinksTabActive: Boolean = true,
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
        drawRect(AppBackground)
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
                .padding(horizontal = 8.dp)
                .padding(top = SearchRowTopPadding, bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            EnergyDrinkSearchBar(
                modifier = Modifier
                    .weight(1f)
                    .widthIn(max = 520.dp)
                    .height(50.dp)
                    .glassContainer(
                        backdrop = listGlassState,
                        shape = RoundedCornerShape(100),
                        tint = GlassPanelTint
                    ),
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                onImeSearch = { keyboardController?.hide() }
            )
            EnergyDrinkViewModeToggle(
                backdrop = listGlassState,
                selectedTab = selectedTab,
                onTabSelected = onTabSelected
            )
            Box {
                GlassCircleButton(
                    onClick = onSortButtonClick,
                    contentDescription = "Sort",
                    icon = Res.drawable.ic_energy_drinks_filter,
                    backdrop = listGlassState,
                    size = 50.dp,
                )
            }
        }

        if (isSortMenuVisible && isDrinksTabActive) {
            SortDropdownPanel(
                backdrop = listGlassState,
                currentOption = sortOption,
                onOptionSelected = onSortOptionSelected,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = SortPanelTopPadding, end = 8.dp)
            )
        }

        if (isSelectionMode) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlassCircleButton(
                    onClick = onCancelSelectionClick,
                    contentDescription = "Cancel selection",
                    icon = Res.drawable.ic_close,
                    backdrop = listGlassState,
                    size = 80.dp,
                    tintBrush = SolidColor(GlassPanelTint),
                    iconTint = AccentWhite
                )
                GlassCircleButton(
                    onClick = onTagSelectedClick,
                    contentDescription = "Tag selected",
                    icon = Res.drawable.ic_tag,
                    backdrop = listGlassState,
                    size = 80.dp,
                    tintBrush = GreenGradientVertical,
                )
                GlassCircleButton(
                    onClick = onDeleteSelectedClick,
                    contentDescription = "Delete selected",
                    icon = Res.drawable.ic_delete,
                    backdrop = listGlassState,
                    size = 80.dp,
                    tintBrush = AccentRedGradientVertical,
                )
            }
        } else {
            onPrimaryActionClick?.let { onClick ->
                GlassCircleButton(
                    onClick = onClick,
                    contentDescription = primaryActionDescription,
                    icon = Res.drawable.ic_energy_drink_add,
                    backdrop = listGlassState,
                    size = 80.dp,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 24.dp)
                )
            }
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
                containerColor = AppBackground,
                titleContentColor = AccentWhite,
                textContentColor = AccentWhite,
                title = { Text("Add tags") },
                text = {
                    Column {
                        TextButton(onClick = onCreateTagClick) {
                            Text("Create new tag", color = TagPinkColor)
                        }
                        if (tags.isEmpty()) {
                            Text("No tags available")
                        } else {
                            tags.forEach { tag ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(
                                        checked = tag.id in selectedTagIds,
                                        onCheckedChange = { onToggleTag(tag.id) },
                                        colors = androidx.compose.material3.CheckboxDefaults.colors(
                                            checkedColor = TagPinkColor,
                                            uncheckedColor = AccentWhite.copy(alpha = 0.6f),
                                            checkmarkColor = AppBackground
                                        )
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
                        enabled = selectedTagIds.isNotEmpty() && !isBulkOperationRunning,
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = TagPinkColor,
                            contentColor = AppBackground
                        )
                    ) { Text("Add") }
                },
                dismissButton = {
                    TextButton(onClick = onDismissTagDialogClick) {
                        Text("Cancel", color = AccentWhite.copy(alpha = 0.7f))
                    }
                }
            )
        }
    }
}
