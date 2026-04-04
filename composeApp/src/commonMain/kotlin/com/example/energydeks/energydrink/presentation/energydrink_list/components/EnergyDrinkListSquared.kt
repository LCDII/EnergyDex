package com.example.energydeks.energydrink.presentation.energydrink_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.energydeks.energydrink.domain.EnergyDrink
import kotlin.time.Instant

@Composable
fun EnergyDrinkListSquared (
    energyDrinks: List<EnergyDrink>,
    onEnergyDrinkClick: (EnergyDrink) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyGridState = rememberLazyGridState(),
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        state = scrollState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = energyDrinks,
            key = { it.id }
        ) { energyDrink ->
            EnergyDrinkItemSquared(
                energyDrink = energyDrink,
                onClick = {
                    onEnergyDrinkClick(energyDrink)
                },
                modifier = Modifier
                    .widthIn(max = 600.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun TmpPreviewEnergyDrinkListSquared() {

    EnergyDrinkListSquared(
        energyDrinks = List(100) { index ->
            EnergyDrink(
                id = index.toLong(),
                name = "Monster Energy White",
                description=null,
                rating = 10.0,
                createdAt = Instant.parse("2006-10-05T12:00:00Z"),
                updatedAt = Instant.parse("2006-10-05T12:00:00Z"),
                imagePath = null,
                tags = listOf("Good AF", "Chuds Drink", "Fuck Foids")
            )
        },
        onEnergyDrinkClick = {}
    )
}