package com.example.energydex.presentation.shared.components

import androidx.compose.foundation.background
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.energydex.core.domain.EnergyDrinkListSortOptions
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.PrimaryOrange
import com.example.energydex.core.presentation.PrimaryPurple
import com.example.energydex.core.presentation.SecondaryOrange
import com.example.energydex.core.presentation.UiText
import com.example.energydex.domain.energydrink.model.EnergyDrink
import energydex.composeapp.generated.resources.Res
import energydex.composeapp.generated.resources.ic_energy_drink_add
import energydex.composeapp.generated.resources.ic_energy_drinks_filter
import energydex.composeapp.generated.resources.no_search_results
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun EnergyDrinkListContent(
    modifier: Modifier = Modifier,
    searchQuery: String,
    searchResult: List<EnergyDrink>,
    selectedTab: EnergyDrinkSectionTab,
    isLoading: Boolean,
    errorMessage: UiText?,
    selectedDrinkIds: Set<Long> = emptySet(),
    sortOption: EnergyDrinkListSortOptions,
    isSortMenuVisible: Boolean,
    onSearchQueryChange: (String) -> Unit,
    onTabSelected: (EnergyDrinkSectionTab) -> Unit,
    onEnergyDrinkClick: (EnergyDrink) -> Unit,
    onSortButtonClick: () -> Unit,
    onSortOptionSelected: (EnergyDrinkListSortOptions) -> Unit,
    onPrimaryActionClick: (() -> Unit)? = null,
    primaryActionDescription: String = ""
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = PrimaryPurple
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        EnergyDrinkSearchBar(
                            modifier = Modifier
                                .widthIn(max = 400.dp)
                                .weight(3f)
                                .fillMaxWidth()
                                .padding(16.dp),
                            searchQuery = searchQuery,
                            onSearchQueryChange = onSearchQueryChange,
                            onImeSearch = { keyboardController?.hide() }
                        )
                        EnergyDrinkViewModeToggle(
                            modifier = Modifier.weight(1f),
                            selectedTab = selectedTab,
                            onTabSelected = onTabSelected
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        when {
                            isLoading -> CircularProgressIndicator()
                            errorMessage != null -> Text(
                                text = errorMessage.asString(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineSmall,
                                color = PrimaryOrange
                            )
            searchResult.isEmpty() -> Text(
                                text = stringResource(Res.string.no_search_results),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineSmall,
                                color = PrimaryOrange
                            )
                            selectedTab == EnergyDrinkSectionTab.SQUARED -> {
                                val scrollState = rememberLazyGridState()
                                LaunchedEffect(searchResult) {
                                    scrollState.animateScrollToItem(0)
                                }
                                EnergyDrinkListSquared(
                                    energyDrinks = searchResult,
                                    onEnergyDrinkClick = onEnergyDrinkClick,
                                    scrollState = scrollState,
                                    selectedDrinkIds = selectedDrinkIds
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
                                    scrollState = scrollState,
                                    selectedDrinkIds = selectedDrinkIds
                                )
                            }
                        }
                    }
                }
            }
        }

        EnergyDrinkFloatingActionButtons(
            isSortMenuVisible = isSortMenuVisible,
            sortOption = sortOption,
            onSortButtonClick = onSortButtonClick,
            onSortOptionSelected = onSortOptionSelected,
            onPrimaryActionClick = onPrimaryActionClick,
            primaryActionDescription = primaryActionDescription
        )
    }
}

@Composable
private fun EnergyDrinkFloatingActionButtons(
    isSortMenuVisible: Boolean,
    sortOption: EnergyDrinkListSortOptions,
    onSortButtonClick: () -> Unit,
    onSortOptionSelected: (EnergyDrinkListSortOptions) -> Unit,
    onPrimaryActionClick: (() -> Unit)?,
    primaryActionDescription: String
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom
    ) {
        FloatingActionButton(
            onClick = onSortButtonClick,
            elevation = FloatingActionButtonDefaults.elevation(4.dp),
            containerColor = Color.Transparent,
            modifier = Modifier.size(56.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PrimaryOrange, CircleShape)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_energy_drinks_filter),
                    contentDescription = "Sort",
                    tint = AccentWhite,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

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

        onPrimaryActionClick?.let { onClick ->
            Spacer(modifier = Modifier.width(16.dp))

            FloatingActionButton(
                onClick = onClick,
                containerColor = Color.Transparent,
                elevation = FloatingActionButtonDefaults.elevation(4.dp),
                modifier = Modifier.size(56.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(listOf(PrimaryOrange, SecondaryOrange)),
                            CircleShape
                        )
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_energy_drink_add),
                        contentDescription = primaryActionDescription,
                        tint = AccentWhite,
                        modifier = Modifier.padding(16.dp)
                    )
                }
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
