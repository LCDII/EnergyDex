package com.example.energydex.presentation.tag.tag_section.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.energydex.core.presentation.AppBackground
import com.example.energydex.core.presentation.GreenGradientHorizontal
import com.example.energydex.core.presentation.glassContainerColored
import com.example.energydex.core.presentation.glassThumb
import com.example.energydex.core.presentation.tagGradient
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.domain.tag.model.TagWithEnergyDrinks
import com.kashif_e.backdrop.backdrops.rememberCanvasBackdrop
import energydex.composeapp.generated.resources.Res
import energydex.composeapp.generated.resources.ic_image_placeholder
import org.jetbrains.compose.resources.painterResource

private sealed interface TagCardSlot {
    data class Drink(val drink: EnergyDrink) : TagCardSlot
    object Empty : TagCardSlot
    object Ellipsis : TagCardSlot
}

@Composable
fun TagCard(
    model: TagWithEnergyDrinks,
    onTagClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tagBrush = tagGradient(model.tag.color)
    val slots = buildTagCardSlots(model.drinks)
    val backdrop = rememberCanvasBackdrop { drawRect(AppBackground) }

    val cardShape = RoundedCornerShape(24.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(cardShape)
            .clickable(onClick = onTagClick)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.Transparent
        ) {
            Column(
                modifier = Modifier
                    .glassContainerColored(
                        backdrop = backdrop,
                        shape = cardShape,
                        tintBrush = tagBrush
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        DrinkSlotRow(slots.take(5))
                        DrinkSlotRow(slots.drop(5).take(5))
                    }
                }
                Spacer(modifier = Modifier.height(48.dp))
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(48.dp)
                .glassThumb(
                    backdrop = backdrop,
                    shape = cardShape,
                    tintBrush = GreenGradientHorizontal
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = model.tag.name,
                color = AppBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

private fun buildTagCardSlots(drinks: List<EnergyDrink>): List<TagCardSlot> {
    if (drinks.size > 10) {
        return drinks.take(9).map { TagCardSlot.Drink(it) } + TagCardSlot.Ellipsis
    }
    return (0 until 10).map { index ->
        if (index < drinks.size) TagCardSlot.Drink(drinks[index]) else TagCardSlot.Empty
    }
}

@Composable
private fun DrinkSlotRow(
    slots: List<TagCardSlot>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        slots.forEach { slot ->
            when (slot) {
                TagCardSlot.Empty -> Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                )

                TagCardSlot.Ellipsis -> Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "...",
                        color = Color.White,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                }

                is TagCardSlot.Drink -> {
                    val drink = slot.drink
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (drink.imagePath == null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = drink.name,
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
                        } else {
                            AsyncImage(
                                model = drink.imagePath,
                                contentDescription = drink.name,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                error = painterResource(Res.drawable.ic_image_placeholder)
                            )
                        }
                    }
                }
            }
        }
    }
}
