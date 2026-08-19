package com.example.energydex.presentation.main_screen


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
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
import com.example.energydex.presentation.main_screen.components.UseFullGlassTab
import com.example.energydex.presentation.main_screen.components.glassTabCapsuleFull
import com.example.energydex.presentation.main_screen.components.glassTabCapsuleLean
import com.example.energydex.presentation.main_screen.components.glassTabPlaqueFull
import com.example.energydex.presentation.main_screen.components.glassTabPlaqueLean
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
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .clip(RoundedCornerShape(28.dp))
                .then(
                    if (UseFullGlassTab) {
                        Modifier.glassTabPlaqueFull(plaqueBackdrop)
                    } else {
                        Modifier.glassTabPlaqueLean(plaqueBackdrop)
                    }
                )
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                val halfWidth = maxWidth / 2
                val isTagsSelected = state.selectedTabIndex == MainScreenTab.TAGS
                val targetOffset = if (isTagsSelected) halfWidth else 0.dp
                val thumbOffset by animateDpAsState(
                    targetValue = targetOffset,
                    animationSpec = tween(durationMillis = 250),
                    label = "tabThumbOffset"
                )

                val capsuleShape = RoundedCornerShape(percent = 50)

                Box(
                    modifier = Modifier
                        .offset(x = thumbOffset)
                        .width(halfWidth)
                        .fillMaxHeight()
                        .clip(capsuleShape)
                        .then(
                            if (UseFullGlassTab) {
                                Modifier.glassTabCapsuleFull(plaqueBackdrop)
                            } else {
                                Modifier.glassTabCapsuleLean(plaqueBackdrop)
                            }
                        )
                )

                Row(modifier = Modifier.fillMaxSize()) {
                    TabSegment(
                        title = "Drinks",
                        selected = !isTagsSelected,
                        onClick = {
                            onAction(MainScreenAction.OnTabSelected(MainScreenTab.ENERGY_DRINKS))
                        },
                        modifier = Modifier.weight(1f)
                    )
                    TabSegment(
                        title = "Tags",
                        selected = isTagsSelected,
                        onClick = {
                            onAction(MainScreenAction.OnTabSelected(MainScreenTab.TAGS))
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun TabSegment(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
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
            color = AccentWhite,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                letterSpacing = 0.5.sp
            )
        )
    }
}
