package dev.lcdii.energydex.presentation.tag.tag_drink_selection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.lcdii.energydex.core.presentation.AppBackground
import dev.lcdii.energydex.core.presentation.ErrorRed
import dev.lcdii.energydex.core.presentation.GreenGradientVertical
import dev.lcdii.energydex.core.presentation.glassThumb
import dev.lcdii.energydex.core.presentation.tagGradient
import dev.lcdii.energydex.presentation.shared.components.EnergyDrinkListContent
import dev.lcdii.energydex.presentation.shared.components.GlassBackButton
import dev.lcdii.energydex.presentation.shared.components.GlassCircleButton
import com.kashif_e.backdrop.backdrops.layerBackdrop
import com.kashif_e.backdrop.backdrops.rememberLayerBackdrop
import energydex.app.shared.generated.resources.Res
import energydex.app.shared.generated.resources.ic_check

@Composable
fun TagDrinkSelectionScreenRoot(
    viewModel: TagDrinkSelectionViewModel,
    onSaved: () -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) onSaved()
    }

    TagDrinkSelectionScreen(
        state = state,
        onBack = onBack,
        onAction = viewModel::onAction
    )
}

@Composable
private fun TagDrinkSelectionScreen(
    state: TagDrinkSelectionState,
    onBack: () -> Unit,
    onAction: (TagDrinkSelectionAction) -> Unit
) {
    val backdrop = rememberLayerBackdrop()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .layerBackdrop(backdrop)
        ) {
            if (state.currentTag == null && state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage.asString(),
                    color = ErrorRed,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                EnergyDrinkListContent(
                    modifier = Modifier.fillMaxWidth(),
                    searchQuery = state.searchQuery,
                    searchResult = state.energyDrinks,
                    selectedTab = state.selectedTab,
                    isLoading = state.isLoading,
                    errorMessage = state.errorMessage,
                    selectedDrinkIds = state.selectedDrinkIds,
                    sortOption = state.sortOption,
                    isSortMenuVisible = state.isSortMenuVisible,
                    onSearchQueryChange = {
                        onAction(TagDrinkSelectionAction.OnSearchQueryChange(it))
                    },
                    onTabSelected = {
                        onAction(TagDrinkSelectionAction.OnTabSelected(it))
                    },
                    onEnergyDrinkClick = {
                        onAction(TagDrinkSelectionAction.OnDrinkToggle(it.id))
                    },
                    onSortButtonClick = {
                        onAction(TagDrinkSelectionAction.OnSortButtonClick)
                    },
                    onSortOptionSelected = {
                        onAction(TagDrinkSelectionAction.OnSortOptionSelected(it))
                    }
                )
            }
        }

        val titleShape = androidx.compose.foundation.shape.RoundedCornerShape(50)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 8.dp, vertical = 12.dp)
                .height(50.dp)
        ) {
            state.currentTag?.let { tag ->
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(180.dp)
                        .height(50.dp)
                        .clip(titleShape)
                        .glassThumb(
                            backdrop = backdrop,
                            shape = titleShape,
                            tintBrush = tagGradient(tag.color)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tag.name,
                        color = AppBackground,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlassBackButton(
                    backdrop = backdrop,
                    onClick = onBack
                )
            }
        }

        GlassCircleButton(
            onClick = {
                if (!state.isSaving) {
                    onAction(TagDrinkSelectionAction.OnSaveClick)
                }
            },
            contentDescription = if (state.isSaving) "Saving" else "Save",
            icon = Res.drawable.ic_check,
            backdrop = backdrop,
            size = 80.dp,
            tintBrush = GreenGradientVertical,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        )
    }
}
