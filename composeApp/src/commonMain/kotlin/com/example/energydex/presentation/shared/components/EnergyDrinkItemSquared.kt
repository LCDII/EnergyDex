package com.example.energydex.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.PrimaryOrange
import com.example.energydex.core.presentation.GlassCardTint
import com.example.energydex.core.presentation.TagPurpleColor
import com.example.energydex.core.presentation.glassBackdrop
import com.example.energydex.core.presentation.ratingStarTint
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.kashif_e.backdrop.Backdrop
import com.kashif_e.backdrop.shadow.InnerShadow
import energydex.composeapp.generated.resources.Res
import energydex.composeapp.generated.resources.ic_image_placeholder
import energydex.composeapp.generated.resources.ic_star
import org.jetbrains.compose.resources.painterResource


@Composable
fun EnergyDrinkItemSquared(
    energyDrink: EnergyDrink,
    onClick: () -> Unit,
    onLongClick: () -> Unit = {},
    onSelectionClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    isSelectionMode: Boolean = false,
    cardBackdrop: Backdrop
) {
    val shape = RoundedCornerShape(24.dp)
    val cardInnerShadow = InnerShadow(radius = 4.dp)
    val hasPhoto = energyDrink.imagePath != null
    val hasTags = energyDrink.tags.isNotEmpty()
    val squaredCardHeight = 200.dp
    val cardInnerPadding = 12.dp

    Box(modifier = modifier) {
        Surface(
            shape = shape,
            modifier = Modifier
                .fillMaxWidth()
                .height(squaredCardHeight)
                .clip(shape)
                .glassBackdrop(
                    backdrop = cardBackdrop,
                    shape = shape,
                    tint = if (isSelected) {
                        PrimaryOrange.copy(alpha = 0.28f)
                    } else {
                        GlassCardTint
                    },
                    blurRadius = 14.dp,
                    saturation = 1.15f,
                    innerShadow = { cardInnerShadow }
                )
                .border(
                    width = if (isSelected) 3.dp else 0.dp,
                    color = if (isSelected) PrimaryOrange else Color.Transparent,
                    shape = shape
                )
                .combinedClickable(
                    onClick = if (isSelectionMode) onSelectionClick else onClick,
                    onLongClick = onLongClick
                ),
            color = Color.Transparent
        ) {
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
            ) {
                when {
                    !hasPhoto && !hasTags -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(cardInnerPadding),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            DrinkName(energyDrink.name)
                            DrinkRating(energyDrink.rating)
                        }
                    }

                    !hasPhoto -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(cardInnerPadding)
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                DrinkName(energyDrink.name)
                                DrinkRating(energyDrink.rating)
                            }
                            TagPack(
                                tags = energyDrink.tags,
                                backdrop = cardBackdrop
                            )
                        }
                    }

                    !hasTags -> {
                        Row(modifier = Modifier.fillMaxSize()) {
                                DrinkPhoto(
                                    energyDrink = energyDrink,
                                    shape = shape,
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                            )
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .padding(cardInnerPadding),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                DrinkName(energyDrink.name)
                                DrinkRating(energyDrink.rating)
                            }
                        }
                    }

                    else -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                            ) {
                                DrinkPhoto(
                                    energyDrink = energyDrink,
                                    shape = shape,
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                )
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .padding(cardInnerPadding),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    DrinkName(energyDrink.name)
                                    DrinkRating(energyDrink.rating)
                                }
                            }
                            TagPack(
                                tags = energyDrink.tags,
                                backdrop = cardBackdrop,
                                modifier = Modifier
                                    .padding(horizontal = cardInnerPadding, vertical = cardInnerPadding)
                            )
                        }
                    }
                }
            }
        }

        if (isSelected) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(30.dp)
                    .background(PrimaryOrange, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("✓", color = AccentWhite, fontSize = 18.sp)
            }
        }
    }
}

@Composable
private fun DrinkPhoto(
    energyDrink: EnergyDrink,
    shape: Shape,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.clip(shape),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = energyDrink.imagePath,
            contentDescription = energyDrink.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop,
            error = painterResource(Res.drawable.ic_image_placeholder),
            placeholder = painterResource(Res.drawable.ic_image_placeholder)
        )
    }
}

@Composable
private fun DrinkName(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = name,
        color = AccentWhite,
        style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.SemiBold
        ),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center,
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
    )
}

@Composable
private fun DrinkRating(
    rating: Double?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_star),
            contentDescription = null,
            tint = if (rating != null && rating >= 10.0) TagPurpleColor else ratingStarTint(rating),
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = rating?.toString() ?: "0.0",
            color = AccentWhite,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 18.sp
        )
    }
}
