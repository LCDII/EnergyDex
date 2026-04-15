package com.example.energydeks.presentation.main_screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.energydeks.domain.energydrink.model.EnergyDrink
import com.example.energydeks.domain.tag.model.Tag
import com.example.energydeks.presentation.energydrink.energydrink_section.EnergyDrinkSectionRoot
import com.example.energydeks.presentation.main_screen.components.MainScreenTab

@Preview
@Composable
fun MainScreenPreview(){
    MainScreenRoot(
        onEnergyDrinkClick = {},
        onAddEnergyDrinkButtonClick = {},
        onTagClick = {}
    )
}

@Composable
fun MainScreenRoot(
    viewModel: MainScreenViewModel = MainScreenViewModel(),
    onEnergyDrinkClick: (EnergyDrink) -> Unit,
    onAddEnergyDrinkButtonClick: () -> Unit,
    onTagClick: (Tag) -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    MainScreen(
        state = state,
        onAction = { action ->
            when(action){
                is MainScreenAction.OnTabSelected -> {
                    viewModel.onAction(action)
                }
            }
        },
        onEnergyDrinkClick = onEnergyDrinkClick,
        onAddEnergyDrinkButtonClick = onAddEnergyDrinkButtonClick,
        onTagClick = onTagClick
    )
}




@Composable
fun MainScreen(
    state: MainScreenState,
    onAction: (MainScreenAction)-> Unit,
    onEnergyDrinkClick: (EnergyDrink) -> Unit,
    onAddEnergyDrinkButtonClick: () -> Unit,
    onTagClick: (Tag) -> Unit
){

    val pagerState = rememberPagerState{ MainScreenTab.entries.size }

    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex.ordinal)
    }

    LaunchedEffect(pagerState.currentPage){
        if(pagerState.currentPage != state.selectedTabIndex.ordinal){
            onAction(MainScreenAction.OnTabSelected(MainScreenTab.entries[pagerState.currentPage]))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1F1038))//TODO
            .statusBarsPadding(),
    ) {
        PrimaryTabRow(
            selectedTabIndex = state.selectedTabIndex.ordinal,
            containerColor = Color(0xFF1F1038),//TODO
            modifier = Modifier
                .padding(vertical = 12.dp)
                .widthIn(max = 700.dp)
                .fillMaxWidth(),
            indicator = {}
        ) {
            Tab(
                selected = state.selectedTabIndex == MainScreenTab.ENERGY_DRINKS,
                onClick = {
                    onAction(MainScreenAction.OnTabSelected(MainScreenTab.ENERGY_DRINKS))
                },
                modifier = Modifier.weight(1f),
                selectedContentColor = Color(0xFFFF7700),
                unselectedContentColor = Color.Gray,

            ) {
                Text(
                    text = "Drinks",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Tab(
                selected = state.selectedTabIndex == MainScreenTab.TAGS,
                onClick = {
                    onAction(MainScreenAction.OnTabSelected(MainScreenTab.TAGS))
                },
                modifier = Modifier.weight(1f),
                selectedContentColor = Color(0xFFFF7700),
                unselectedContentColor = Color.Gray,
                ) {
                Text(
                    text = "Tags",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { pageIndex ->
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                when(pageIndex){
                    MainScreenTab.ENERGY_DRINKS.ordinal->{
                        if(state.isLoading) {
                            CircularProgressIndicator()
                        } else {
                            when {
                                state.errorMessage != null -> {
                                    Text(
                                        text = state.errorMessage.asString(),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.headlineSmall
                                    )
                                }

                                else -> {
                                    EnergyDrinkSectionRoot(
                                        onEnergyDrinkClick = {
                                            onEnergyDrinkClick(it)
                                        },
                                        onAddEnergyDrinkButtonClick = {
                                            onAddEnergyDrinkButtonClick()
                                        }
                                    )
                                }
                            }
                        }
                    }

                    MainScreenTab.TAGS.ordinal-> {
                        if(state.isLoading) {
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

                                else -> {
                                    EnergyDrinkSectionRoot(
                                        onEnergyDrinkClick = {

                                        },
                                        onAddEnergyDrinkButtonClick = {

                                        }
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