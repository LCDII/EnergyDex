package com.example.energydex.presentation.tag.tag_section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.energydex.domain.tag.model.Tag
import com.example.energydex.presentation.shared.components.GlassCircleButton
import com.example.energydex.presentation.tag.tag_section.components.TagCard
import com.kashif_e.backdrop.backdrops.layerBackdrop
import com.kashif_e.backdrop.backdrops.rememberLayerBackdrop
import energydex.composeapp.generated.resources.Res
import energydex.composeapp.generated.resources.ic_energy_drink_add
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TagSectionRoot(
    viewModel: TagSectionViewModel = koinViewModel(),
    onTagClick: (Tag) -> Unit,
    onCreateTagClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    TagSection(
        state = state,
        onAction = { action ->
            when (action) {
                is TagSectionAction.OnTagClick -> onTagClick(action.tag)
                TagSectionAction.OnCreateTagClick -> onCreateTagClick()
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun TagSection(
    state: TagSectionState,
    onAction: (TagSectionAction) -> Unit
) {
    val backdrop = rememberLayerBackdrop()

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
            state.errorMessage != null -> Text(
                text = state.errorMessage.asString(),
                modifier = Modifier.align(Alignment.Center)
            )
            state.tags.isEmpty() -> Text(
                text = "No tags yet",
                modifier = Modifier.align(Alignment.Center)
            )
            else -> LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .layerBackdrop(backdrop),
                contentPadding = PaddingValues(
                    start = 8.dp,
                    end = 8.dp,
                    top = 84.dp,
                    bottom = 8.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.tags, key = { it.tag.id }) { model ->
                    TagCard(
                        model = model,
                        onTagClick = {
                            onAction(TagSectionAction.OnTagClick(model.tag))
                        }
                    )
                }
            }
        }

        GlassCircleButton(
            onClick = { onAction(TagSectionAction.OnCreateTagClick) },
            contentDescription = "Create tag",
            icon = Res.drawable.ic_energy_drink_add,
            backdrop = backdrop,
            size = 80.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        )
    }
}