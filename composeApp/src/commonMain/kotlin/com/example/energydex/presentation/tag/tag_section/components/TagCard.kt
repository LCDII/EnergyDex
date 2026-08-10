package com.example.energydex.presentation.tag.tag_section.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.energydex.core.presentation.SecondaryPurple
import com.example.energydex.core.presentation.tagColor
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.presentation.tag.tag_section.TagCardUiModel
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
    model: TagCardUiModel,
    onTagClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tagColor = tagColor(model.tag.color)
    val slots = buildTagCardSlots(model.drinks)

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onTagClick),
        shape = RoundedCornerShape(24.dp),
        color = SecondaryPurple
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(tagColor.copy(alpha = 0.8f))
                    .padding(12.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    DrinkSlotRow(slots.take(5), tagColor)
                    DrinkSlotRow(slots.drop(5).take(5), tagColor)
                }
            }
            Text(
                text = model.tag.name,
                color = AccentWhite,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                textAlign = TextAlign.Center
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
    slots: List<TagCardSlot>,
    tagColor: Color
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
                        .clip(RoundedCornerShape(12.dp))
                        .background(tagColor.copy(alpha = 0.35f)),
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
                            Icon(
                                painter = painterResource(Res.drawable.ic_image_placeholder),
                                contentDescription = drink.name,
                                tint = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                            )
                        } else {
                            AsyncImage(
                                model = drink.imagePath,
                                contentDescription = drink.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f),
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