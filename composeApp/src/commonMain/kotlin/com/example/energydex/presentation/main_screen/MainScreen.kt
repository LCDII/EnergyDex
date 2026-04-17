package com.example.energydex.presentation.main_screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.domain.tag.model.Tag
import com.example.energydex.presentation.energydrink.energydrink_section.EnergyDrinkSectionRoot
import com.example.energydex.presentation.main_screen.components.MainScreenTab
import org.koin.compose.viewmodel.koinViewModel

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
    viewModel: MainScreenViewModel = koinViewModel(),
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
            indicator = {
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(state.selectedTabIndex.ordinal)
                        .padding(horizontal = 24.dp)
                        .height(3.dp)
                        .clip(RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFFFF7700),
                                    Color(0xFFFF5500)
                                )
                            )
                        )
                )
            },
            divider = {}
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { onAction(MainScreenAction.OnTabSelected(MainScreenTab.ENERGY_DRINKS)) }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Drinks",
                    textAlign = TextAlign.Center,
                    color = if (state.selectedTabIndex == MainScreenTab.ENERGY_DRINKS)
                        Color(0xFFFF7700) else Color(0x73995EFF),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = if (state.selectedTabIndex == MainScreenTab.ENERGY_DRINKS)
                            FontWeight.SemiBold
                        else
                            FontWeight.Normal,
                        letterSpacing = 0.5.sp
                    )
                )
            }

            // Кастомный таб для Tags
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { onAction(MainScreenAction.OnTabSelected(MainScreenTab.TAGS)) }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tags",
                    textAlign = TextAlign.Center,
                    color = if (state.selectedTabIndex == MainScreenTab.TAGS)
                        Color(0xFFFF7700) else Color(0x73995EFF),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = if (state.selectedTabIndex == MainScreenTab.TAGS)
                            FontWeight.SemiBold
                        else
                            FontWeight.Normal,
                        letterSpacing = 0.5.sp
                    )
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
                    MainScreenTab.ENERGY_DRINKS.ordinal -> {
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
                                    Surface {
                                        Text(
                                            text="Tags"
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
}