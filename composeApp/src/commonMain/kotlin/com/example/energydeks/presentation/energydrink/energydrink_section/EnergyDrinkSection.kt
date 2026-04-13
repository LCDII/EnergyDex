package com.example.energydeks.presentation.energydrink.energydrink_section

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.energydeks.domain.energydrink.model.EnergyDrink
import com.example.energydeks.presentation.energydrink.energydrink_section.components.EnergyDrinkListLonged
import com.example.energydeks.presentation.energydrink.energydrink_section.components.EnergyDrinkListSquared
import com.example.energydeks.presentation.energydrink.energydrink_section.components.EnergyDrinkSearchBar
import com.example.energydeks.presentation.energydrink.energydrink_section.components.EnergyDrinkSectionTab
import energydeks.composeapp.generated.resources.Res
import energydeks.composeapp.generated.resources.close_hint
import energydeks.composeapp.generated.resources.ic_close
import energydeks.composeapp.generated.resources.no_search_results
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

//@Preview
//@Composable
//fun TmpEnergyDrinkSectionRootPreview() {
//    //Calling the stateless EnergyDrinkSection instead of EnergyDrinkSectionRoot
//    // to avoid the "KoinApplication has not been started" error in the preview.
//    MaterialTheme {
//        EnergyDrinkSectionRoot(
//            onEnergyDrinkClick = {}
//        )
//    }
//}
@Preview
@Composable
fun TmpEnergyDrinkSectionPreview() {
    //Calling the stateless EnergyDrinkSection instead of EnergyDrinkSectionRoot
    // to avoid the "KoinApplication has not been started" error in the preview.
    MaterialTheme {
        EnergyDrinkSection(
            state = EnergyDrinkSectionState(
                isLoading = false
            ),
            onAction = {}
        )
    }
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
                is EnergyDrinkSectionAction.OnEnergyDrinkNavigateClick ->
                    onEnergyDrinkClick(action.energyDrink)
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

    val pagerState = rememberPagerState { EnergyDrinkSectionTab.entries.size } //Tab state for Tags Drinks; 2 because 2 tabs


//    LaunchedEffect(state.selectedTabIndex) {
//        pagerState.animateScrollToPage(state.selectedTabIndex.ordinal) //TODO animation
//    }//smooth scroll between tabs

    LaunchedEffect(pagerState.currentPage) {
        onAction(EnergyDrinkSectionAction.OnTabSelected(EnergyDrinkSectionTab.entries[pagerState.currentPage]))
    }//works with previous launcher: say viewmodel to rewrite what list of items

    Box(modifier = Modifier.fillMaxSize())
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFD8B4FE))
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color(0xFF1F1038),
                shape = RoundedCornerShape(
                    topStart = 32.dp,
                    topEnd = 32.dp
                )
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

                        TabToggleButtons(
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
                //TODO
            },
            onSortClick = {
                //todo
            }
        )
    }
}

@Composable
private fun TabToggleButtons(
    modifier: Modifier = Modifier,
    selectedTab: EnergyDrinkSectionTab,
    onTabSelected: (EnergyDrinkSectionTab) -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0x73995EFF)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        EnergyDrinkSectionTab.entries.forEach { tab ->
            IconToggleButton(
                checked = selectedTab == tab,
                onCheckedChange = { onTabSelected(tab) },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    painter = when (tab) {
                        EnergyDrinkSectionTab.SQUARED -> painterResource(Res.drawable.ic_close)//TODO fix
                        EnergyDrinkSectionTab.LONGED -> painterResource(Res.drawable.ic_close)//TODO fix
                    },
                    contentDescription = tab.name,
                    tint = if (selectedTab == tab)
                        Color(0xFFFFFFFF)
                    else
                        Color(0xFFFFFFFF)
                )
            }
        }
    }
}

@Composable
private fun FloatingActionButtons(
    modifier: Modifier = Modifier,
    onCreateClick: () -> Unit,
    onSortClick: () -> Unit
) {
    // Create button
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        FloatingActionButton(
            onClick = onCreateClick,
            containerColor = Color(0xFFFF7700),
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(56.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_close),//TODO fix
                contentDescription = stringResource(Res.string.close_hint),//TODO fix
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }

    // Sort button
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = onSortClick,
            containerColor = Color(0xFFFF7700),
            modifier = Modifier.size(56.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_close),//TODO fix
                contentDescription = stringResource(Res.string.close_hint),//TODO fix
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}