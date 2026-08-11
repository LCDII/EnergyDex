package com.example.energydex.presentation.energydrink.energydrink_section

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.domain.tag.model.Tag
import com.example.energydex.presentation.shared.components.EnergyDrinkListContent
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Instant

@Preview
@Composable
fun TmpEnergyDrinkSectionPreview() {
    //Calling the stateless EnergyDrinkSection instead of EnergyDrinkSectionRoot
    // to avoid the "KoinApplication has not been started" error in the preview.
    MaterialTheme {
        EnergyDrinkSection(
            state = EnergyDrinkSectionState(
                isLoading = false,
                searchResult = List(100) { index ->
                    EnergyDrink(
                        id = index.toLong(),
                        name = "Monster Energy White",
                        amount = 1,
                        description=null,
                        rating = 10.0,
                        createdAt = Instant.parse("2006-10-05T12:00:00Z"),
                        updatedAt = Instant.parse("2006-10-05T12:00:00Z"),
                        imagePath = null,
                        tags = listOf(
                            Tag(
                                id = 0,
                                name = "Good AF",
                                color = "#000000"
                            ),
                            Tag(
                                id = 0,
                                name = "Chuds Drink",
                                color = "#000000"
                            )
                        )
                    )
                }
            ),
            onAction = {}
        )
    }
}

@Composable
fun EnergyDrinkSectionRoot(
    viewModel: EnergyDrinkSectionViewModel = koinViewModel(),
    onEnergyDrinkClick: (EnergyDrink) -> Unit,
    onAddEnergyDrinkButtonClick: () -> Unit
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
        }
    )

}

@Composable
fun EnergyDrinkSection(
    state: EnergyDrinkSectionState,
    onAction: (EnergyDrinkSectionAction) -> Unit
) {
    EnergyDrinkListContent(
        searchQuery = state.searchQuery,
        searchResult = state.searchResult,
        selectedTab = state.selectedTabIndex,
        isLoading = state.isLoading,
        errorMessage = state.errorMessage,
        sortOption = state.sortOption,
        isSortMenuVisible = state.isSortMenuVisible,
        onSearchQueryChange = {
            onAction(EnergyDrinkSectionAction.OnSearchQueryChange(it))
        },
        onTabSelected = {
            onAction(EnergyDrinkSectionAction.OnTabSelected(it))
        },
        onEnergyDrinkClick = {
            onAction(EnergyDrinkSectionAction.OnEnergyDrinkNavigateClick(it))
        },
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
