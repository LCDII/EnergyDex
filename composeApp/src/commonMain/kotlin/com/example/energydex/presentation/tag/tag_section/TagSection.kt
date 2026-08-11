package com.example.energydex.presentation.tag.tag_section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.PrimaryOrange
import com.example.energydex.core.presentation.SecondaryOrange
import com.example.energydex.domain.tag.model.Tag
import com.example.energydex.presentation.tag.tag_section.components.TagCard
import energydex.composeapp.generated.resources.Res
import energydex.composeapp.generated.resources.ic_energy_drink_add
import org.jetbrains.compose.resources.painterResource
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
            else -> LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 240.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
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

        FloatingActionButton(
            onClick = {
                onAction(TagSectionAction.OnCreateTagClick)
            },
            containerColor = Color.Transparent,
            elevation = FloatingActionButtonDefaults.elevation(4.dp),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(56.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(PrimaryOrange, SecondaryOrange)
                        ),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_energy_drink_add),
                    contentDescription = "Create tag",
                    tint = AccentWhite,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
