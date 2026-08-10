package com.example.energydex.presentation.tag.tag_section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.energydex.core.presentation.PrimaryOrange
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.domain.tag.model.Tag
import com.example.energydex.presentation.tag.tag_section.components.TagCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TagSectionRoot(
    viewModel: TagSectionViewModel = koinViewModel(),
    onTagClick: (Tag) -> Unit,
    onDrinkClick: (EnergyDrink) -> Unit,
    onCreateTagClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    TagSection(
        state = state,
        onTagClick = onTagClick,
        onDrinkClick = onDrinkClick,
        onCreateTagClick = onCreateTagClick
    )
}

@Composable
private fun TagSection(
    state: TagSectionState,
    onTagClick: (Tag) -> Unit,
    onDrinkClick: (EnergyDrink) -> Unit,
    onCreateTagClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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
                        onTagClick = { onTagClick(model.tag) },
                        onDrinkClick = onDrinkClick
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = onCreateTagClick,
            containerColor = PrimaryOrange,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {
            Text("+")
        }
    }
}
