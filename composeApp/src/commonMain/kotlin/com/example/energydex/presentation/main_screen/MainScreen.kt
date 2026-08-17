package com.example.energydex.presentation.main_screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.energydex.core.presentation.PrimaryPurple
import com.example.energydex.core.presentation.SecondaryPurple
import com.example.energydex.core.presentation.ErrorRed
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.GlassPanelTint
import com.example.energydex.core.presentation.GlassTabBorder
import com.example.energydex.core.presentation.backdropGlass
import com.kashif_e.backdrop.backdrops.layerBackdrop
import com.kashif_e.backdrop.backdrops.rememberLayerBackdrop
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.domain.tag.model.Tag
import com.example.energydex.presentation.energydrink.energydrink_section.EnergyDrinkSectionRoot
import com.example.energydex.presentation.main_screen.components.MainScreenTab
import com.example.energydex.presentation.tag.tag_section.TagSectionRoot
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
    onTagClick: (Tag) -> Unit,
    onCreateTagClick: () -> Unit = {}
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
        onTagClick = onTagClick,
        onCreateTagClick = onCreateTagClick
    )
}




@Composable
fun MainScreen(
    state: MainScreenState,
    onAction: (MainScreenAction)-> Unit,
    onEnergyDrinkClick: (EnergyDrink) -> Unit,
    onAddEnergyDrinkButtonClick: () -> Unit,
    onTagClick: (Tag) -> Unit,
    onCreateTagClick: () -> Unit
){

    val pagerState = rememberPagerState{ MainScreenTab.entries.size }

    val plaqueBackdrop = rememberLayerBackdrop()

    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex.ordinal)
    }

    LaunchedEffect(pagerState.currentPage){
        if(pagerState.currentPage != state.selectedTabIndex.ordinal){
            onAction(MainScreenAction.OnTabSelected(MainScreenTab.entries[pagerState.currentPage]))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryPurple)//TODO
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .layerBackdrop(plaqueBackdrop)
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
                                        },
                                        onCreateTagClick = {
                                            onCreateTagClick()
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
                                         color = ErrorRed
                                    )
                                }

                                else -> {
                                    TagSectionRoot(
                                        onTagClick = onTagClick,
                                        onCreateTagClick = onCreateTagClick
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(vertical = 12.dp)
                .widthIn(max = 340.dp)
                .clip(RoundedCornerShape(28.dp))
                .backdropGlass(
                    backdrop = plaqueBackdrop,
                    shape = RoundedCornerShape(28.dp),
                    tint = GlassPanelTint
                )
        ) {
            Row(modifier = Modifier.height(56.dp)) {
                GlassTabItem(
                    title = "Drinks",
                    selected = state.selectedTabIndex == MainScreenTab.ENERGY_DRINKS,
                    onClick = {
                        onAction(MainScreenAction.OnTabSelected(MainScreenTab.ENERGY_DRINKS))
                    },
                    leftCorner = true,
                    modifier = Modifier.weight(1f)
                )
                GlassTabItem(
                    title = "Tags",
                    selected = state.selectedTabIndex == MainScreenTab.TAGS,
                    onClick = {
                        onAction(MainScreenAction.OnTabSelected(MainScreenTab.TAGS))
                    },
                    leftCorner = false,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun GlassTabItem(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
    leftCorner: Boolean,
    modifier: Modifier = Modifier
) {
    val shape = if (leftCorner) {
        RoundedCornerShape(topStart = 28.dp, bottomStart = 28.dp)
    } else {
        RoundedCornerShape(topEnd = 28.dp, bottomEnd = 28.dp)
    }

    Box(
        modifier = modifier
            .fillMaxHeight()
            .then(
                if (selected) {
                    Modifier.background(
                        brush = GlassTabBorder,
                        shape = shape
                    )
                } else {
                    Modifier
                }
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            color = if (selected) AccentWhite else SecondaryPurple,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                letterSpacing = 0.5.sp
            )
        )
    }
}
