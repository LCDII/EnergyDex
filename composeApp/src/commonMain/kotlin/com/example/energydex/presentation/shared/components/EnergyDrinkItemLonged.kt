package com.example.energydex.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.energydex.domain.energydrink.model.EnergyDrink
import energydex.composeapp.generated.resources.Res
import energydex.composeapp.generated.resources.ic_gem
import energydex.composeapp.generated.resources.ic_image_placeholder
import org.jetbrains.compose.resources.painterResource
import com.example.energydex.core.presentation.SecondaryPurple
import com.example.energydex.core.presentation.SecondaryOrange
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.PrimaryOrange
import com.example.energydex.core.presentation.tagColor
import energydex.composeapp.generated.resources.ic_tag
@Composable
fun EnergyDrinkItemLonged(
    energyDrink: EnergyDrink,
    onClick: () -> Unit,
    onLongClick: () -> Unit = {},
    onSelectionClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    isSelectionMode: Boolean = false
) {
    val shape = RoundedCornerShape(32.dp)

    Box(modifier = modifier) {
        Surface(
            shape = shape,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = if (isSelected) 3.dp else 0.dp,
                    color = if (isSelected) PrimaryOrange else Color.Transparent,
                    shape = shape
                )
                .combinedClickable(
                    onClick = if (isSelectionMode) onSelectionClick else onClick,
                    onLongClick = onLongClick
                ),
            color = if (isSelected) {
                PrimaryOrange.copy(alpha = 0.28f)
            } else {
                SecondaryPurple
            }
        ) {
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(110.dp)
                        .width(110.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (energyDrink.imagePath == null) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_image_placeholder),
                            contentDescription = null,
                            tint = SecondaryOrange,
                            modifier = Modifier.size(48.dp)
                        )
                    } else {
                        AsyncImage(
                            model = energyDrink.imagePath,
                            contentDescription = energyDrink.name,
                            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                            error = painterResource(Res.drawable.ic_image_placeholder)
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text=energyDrink.name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    {
                        if (energyDrink.tags.isNotEmpty()) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_tag),
                                contentDescription = "Tags",
                                tint = tagColor(energyDrink.tags.first().color),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    Text(
                        text = energyDrink.createdAt.toString().substring(0,10)
                    )
                }
                Icon(
                    painter = painterResource(Res.drawable.ic_gem),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text =if(energyDrink.rating == null)
                    {
                        "0.0"
                    } else {
                        "${energyDrink.rating}"
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 24.sp
               )
            }
        }

        if (isSelected) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                    .size(30.dp)
                    .background(PrimaryOrange, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("✓", color = AccentWhite, fontSize = 18.sp)
            }
        }
    }
}
