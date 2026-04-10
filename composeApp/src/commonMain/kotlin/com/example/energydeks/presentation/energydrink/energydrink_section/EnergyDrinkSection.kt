package com.example.energydeks.presentation.energydrink.energydrink_section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.energydeks.core.presentation.TestBackgroundColor
import com.example.energydeks.core.presentation.TestBackgroundSurfaceColor
import com.example.energydeks.domain.energydrink.model.EnergyDrink
import com.example.energydeks.presentation.energydrink.energydrink_section.components.EnergyDrinkListLonged
import com.example.energydeks.presentation.energydrink.energydrink_section.components.EnergyDrinkListSquared
import com.example.energydeks.presentation.energydrink.energydrink_section.components.EnergyDrinkSearchBar
import com.example.energydeks.presentation.energydrink.energydrink_section.components.EnergyDrinkSectionTab
import energydeks.composeapp.generated.resources.Res
import energydeks.composeapp.generated.resources.no_search_results
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Preview
@Composable
fun EnergyDrinkSectionRootPreview() {
    EnergyDrinkSectionRoot(
        onEnergyDrinkClick = {}
    )
}


@Composable
fun EnergyDrinkSectionRoot(
    viewModel: EnergyDrinkSectionViewModel = koinViewModel(),
    onEnergyDrinkClick: (EnergyDrink) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    EnergyDrinkSection(
        state = state,
        onAction = { action ->
            when(action) {
                is EnergyDrinkSectionAction.OnEnergyDrinkClick ->
                    onEnergyDrinkClick(action.energyDrink)
                else -> Unit
            }//can redo it
        }
    )

}

@Composable
fun EnergyDrinkSection(
    state: EnergyDrinkSectionState,
    onAction: (EnergyDrinkSectionAction) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val pagerState = rememberPagerState { EnergyDrinkSectionTab.entries.size } //Tab state for Tags Drinks; 2 because 2 tabs


//    LaunchedEffect(state.selectedTabIndex) {
//        pagerState.animateScrollToPage(state.selectedTabIndex.ordinal) //TODO animation
//    }//smooth scroll between tabs

    LaunchedEffect(pagerState.currentPage) {
        onAction(EnergyDrinkSectionAction.OnTabSelected(EnergyDrinkSectionTab.entries.get(pagerState.currentPage)))
    }//works with previous launcher: say viewmodel to rewrite what list of items

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TestBackgroundColor)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = TestBackgroundSurfaceColor,
            shape = RoundedCornerShape(
                topStart = 32.dp,
                topEnd = 32.dp
            )
        ) {
            EnergyDrinkSearchBar(
                modifier = Modifier.
                    widthIn(max = 400.dp)
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

            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if(state.isLoading) {
                    CircularProgressIndicator()
                } else {
                    when{
                        state.errorMessage != null -> {
                            Text(
                                text = state.errorMessage.asString(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                        state.searchResult.isEmpty() -> {
                            Text(
                                text = stringResource(Res.string.no_search_results),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                        else -> {
                            when(state.selectedTabIndex){
                                EnergyDrinkSectionTab.SQUARED -> {
                                    val searchResultsState = rememberLazyGridState()
                                    LaunchedEffect(state.searchResult) {
                                        searchResultsState.animateScrollToItem(0)
                                    }//scroll to start while search result change
                                    EnergyDrinkListSquared(
                                        energyDrinks = state.searchResult,
                                        onEnergyDrinkClick = {
                                            onAction(EnergyDrinkSectionAction.OnEnergyDrinkClick(it))
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
                                            onAction(EnergyDrinkSectionAction.OnEnergyDrinkClick(it))
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