package dev.lcdii.energydex.presentation.energydrink.energydrink_section

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.lcdii.energydex.domain.energydrink.model.EnergyDrink
import dev.lcdii.energydex.domain.tag.model.Tag
import dev.lcdii.energydex.presentation.shared.components.EnergyDrinkListContent
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Instant


@Composable
fun EnergyDrinkSectionRoot(
    viewModel: EnergyDrinkSectionViewModel = koinViewModel(),
    onEnergyDrinkClick: (EnergyDrink) -> Unit,
    onAddEnergyDrinkButtonClick: () -> Unit,
    onCreateTagClick: () -> Unit = {},
    isDrinksTabActive: Boolean = true
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    EnergyDrinkSection(
        state = state,
        onAction = { action ->
            when(action) {
                is EnergyDrinkSectionAction.OnEnergyDrinkNavigateClick ->
                    onEnergyDrinkClick(action.energyDrink)//navigation
                is EnergyDrinkSectionAction.OnAddEnergyDrink->
                    onAddEnergyDrinkButtonClick()//navigation
                else -> Unit
            }//can redo it
            viewModel.onAction(action)
        },
        onCreateTagClick = onCreateTagClick,
        isDrinksTabActive = isDrinksTabActive
    )

}

@Composable
fun EnergyDrinkSection(
    state: EnergyDrinkSectionState,
    onAction: (EnergyDrinkSectionAction) -> Unit,
    onCreateTagClick: () -> Unit = {},
    isDrinksTabActive: Boolean = true
) {
    EnergyDrinkListContent(
        searchQuery = state.searchQuery,
        searchResult = state.searchResult,
        selectedDrinkIds = state.selectedEnergyDrinkIds.toSet(),
        isSelectionMode = state.isSelectionMode,
        isDeleteDialogVisible = state.isDeleteDialogVisible,
        isTagDialogVisible = state.isTagDialogVisible,
        tags = state.tags,
        selectedTagIds = state.selectedTagIds,
        isBulkOperationRunning = state.isBulkOperationRunning,
        selectedTab = state.selectedTabIndex,
        isLoading = state.isLoading,
        errorMessage = state.errorMessage,
        sortOption = state.sortOption,
        isSortMenuVisible = state.isSortMenuVisible,
        isDrinksTabActive = isDrinksTabActive,
        onSearchQueryChange = {
            onAction(EnergyDrinkSectionAction.OnSearchQueryChange(it))
        },
        onTabSelected = {
            onAction(EnergyDrinkSectionAction.OnTabSelected(it))
        },
        onEnergyDrinkClick = {
            onAction(EnergyDrinkSectionAction.OnEnergyDrinkNavigateClick(it))
        },
        onEnergyDrinkLongClick = {
            onAction(EnergyDrinkSectionAction.OnEnergyDrinkHold(it))
        },
        onEnergyDrinkSelectionClick = {
            onAction(EnergyDrinkSectionAction.OnSelectEnergyDrink(it))
        },
        onCancelSelectionClick = {
            onAction(EnergyDrinkSectionAction.OnCancelSelectionClick)
        },
        onDeleteSelectedClick = {
            onAction(EnergyDrinkSectionAction.OnDeleteSelectedClick)
        },
        onConfirmDeleteSelectedClick = {
            onAction(EnergyDrinkSectionAction.OnConfirmDeleteSelectedClick)
        },
        onDismissDeleteDialogClick = {
            onAction(EnergyDrinkSectionAction.OnDismissDeleteDialogClick)
        },
        onTagSelectedClick = {
            onAction(EnergyDrinkSectionAction.OnTagSelectedClick)
        },
        onToggleTag = {
            onAction(EnergyDrinkSectionAction.OnToggleTag(it))
        },
        onConfirmTagSelectedClick = {
            onAction(EnergyDrinkSectionAction.OnConfirmTagSelectedClick)
        },
        onDismissTagDialogClick = {
            onAction(EnergyDrinkSectionAction.OnDismissTagDialogClick)
        },
        onCreateTagClick = onCreateTagClick,
        onSortButtonClick = {
            onAction(EnergyDrinkSectionAction.OnSortButtonClick)
        },
        onSortOptionSelected = {
            onAction(EnergyDrinkSectionAction.OnSortOptionSelected(it))
        },
        onPrimaryActionClick = {
            onAction(EnergyDrinkSectionAction.OnAddEnergyDrink)
        },
        primaryActionDescription = "Create energy drink"
    )
}
