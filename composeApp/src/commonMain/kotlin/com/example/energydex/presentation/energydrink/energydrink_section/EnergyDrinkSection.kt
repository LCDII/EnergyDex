package com.example.energydex.presentation.energydrink.energydrink_section

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.energydex.core.domain.EnergyDrinkListSortOptions
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.domain.tag.model.Tag
import com.example.energydex.presentation.shared.components.EnergyDrinkListLonged
import com.example.energydex.presentation.shared.components.EnergyDrinkListSquared
import com.example.energydex.presentation.shared.components.EnergyDrinkSearchBar
import com.example.energydex.presentation.shared.components.EnergyDrinkSectionTab
import com.example.energydex.presentation.shared.components.EnergyDrinkViewModeToggle
import energydex.composeapp.generated.resources.Res
import energydex.composeapp.generated.resources.ic_energy_drink_add
import energydex.composeapp.generated.resources.ic_energy_drinks_filter
import energydex.composeapp.generated.resources.ic_energy_drinks_list_view_longed
import energydex.composeapp.generated.resources.ic_energy_drinks_list_view_squared
import energydex.composeapp.generated.resources.no_search_results
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Instant
import com.example.energydex.core.presentation.PrimaryPurple
import com.example.energydex.core.presentation.PrimaryOrange
import com.example.energydex.core.presentation.SecondaryOrange
import com.example.energydex.core.presentation.SecondaryPurple

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
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(modifier = Modifier.fillMaxSize())
    {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                color = PrimaryPurple,
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        EnergyDrinkSearchBar(
                            modifier = Modifier.widthIn(max = 400.dp)
                                .weight(3f)
                                .fillMaxWidth()
                                .padding(16.dp),
                            searchQuery = state.searchQuery,
                            onSearchQueryChange = {
                                onAction(EnergyDrinkSectionAction.OnSearchQueryChange(it))
                            },
                            onImeSearch = {
                                keyboardController?.hide()
                            }
                        )

                        EnergyDrinkViewModeToggle(
                            modifier = Modifier.weight(1f),
                            selectedTab = state.selectedTabIndex,
                            onTabSelected = { tab ->
                                onAction(EnergyDrinkSectionAction.OnTabSelected(tab))
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator()
                        } else {
                            when {
                                state.errorMessage != null -> {
                                    Text(
                                        text = state.errorMessage.asString(),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = PrimaryOrange
                                    )
                                }

                                state.searchResult.isEmpty() -> {
                                    Text(
                                        text = stringResource(Res.string.no_search_results),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = PrimaryOrange
                                    )
                                }

                                else -> {
                                    when (state.selectedTabIndex) {
                                        EnergyDrinkSectionTab.SQUARED -> {
                                            val searchResultsState = rememberLazyGridState()
                                            LaunchedEffect(state.searchResult) {
                                                searchResultsState.animateScrollToItem(0)
                                            }//scroll to start while search result change
                                            EnergyDrinkListSquared(
                                                energyDrinks = state.searchResult,
                                                onEnergyDrinkClick = {
                                                    onAction(
                                                        EnergyDrinkSectionAction.OnEnergyDrinkNavigateClick(
                                                            it
                                                        )
                                                    )
                                                },
                                                scrollState = searchResultsState
                                            )
                                        }

                                        EnergyDrinkSectionTab.LONGED -> {
                                            val searchResultsState = rememberLazyListState()
                                            LaunchedEffect(state.searchResult) {
                                                searchResultsState.animateScrollToItem(0)
                                            }//scroll to start while search result change
                                            EnergyDrinkListLonged(
                                                energyDrinks = state.searchResult,
                                                onEnergyDrinkClick = {
                                                    onAction(
                                                        EnergyDrinkSectionAction.OnEnergyDrinkNavigateClick(
                                                            it
                                                        )
                                                    )
                                                },
                                                scrollState = searchResultsState
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        FloatingActionButtons(
            onCreateClick = {
                onAction(EnergyDrinkSectionAction.OnAddEnergyDrink)
            },
            onSortClick = {
                onAction(EnergyDrinkSectionAction.OnSortButtonClick)
            },
            isSortMenuVisible = state.isSortMenuVisible,
            onDismissSortMenu = {
                onAction(EnergyDrinkSectionAction.OnSortButtonClick)
            },
            sortOption = state.sortOption,
            onSortOptionSelected = { option ->
                onAction(EnergyDrinkSectionAction.OnSortOptionSelected(option))
            }
        )
    }
}

@Composable
private fun FloatingActionButtons(
    modifier: Modifier = Modifier,
    onCreateClick: () -> Unit,
    onSortClick: () -> Unit,
    isSortMenuVisible: Boolean,
    onDismissSortMenu: () -> Unit,
    sortOption: EnergyDrinkListSortOptions,
    onSortOptionSelected: (EnergyDrinkListSortOptions) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom,
    ) {
        // Create button с градиентом
        FloatingActionButton(
            onClick = onSortClick,
            elevation = FloatingActionButtonDefaults.elevation(4.dp),
            containerColor = Color.Transparent,
            modifier = Modifier.size(56.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        PrimaryOrange,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_energy_drinks_filter),
                    contentDescription = "Create",
                    tint = AccentWhite,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        DropdownMenu(
            expanded = isSortMenuVisible,
            onDismissRequest = onDismissSortMenu
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

        Spacer(modifier = Modifier.width(16.dp))

        // Sort button с градиентом
        FloatingActionButton(
            onClick = onCreateClick,
            containerColor = Color.Transparent,
            elevation = FloatingActionButtonDefaults.elevation(4.dp),
            modifier = Modifier.size(56.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                PrimaryOrange, // оранжевый
                                SecondaryOrange
                            )
                        ),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_energy_drink_add),
                    contentDescription = "Sort",
                    tint = AccentWhite,
                    modifier = Modifier.padding(16.dp)
                )
            }
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
