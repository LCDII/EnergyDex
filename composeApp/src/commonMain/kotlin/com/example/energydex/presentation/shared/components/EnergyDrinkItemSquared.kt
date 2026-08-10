package com.example.energydex.presentation.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.energydex.core.presentation.SecondaryPurple
import com.example.energydex.core.presentation.SecondaryOrange
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.PrimaryOrange
import com.example.energydex.domain.energydrink.model.EnergyDrink
import energydex.composeapp.generated.resources.Res
import energydex.composeapp.generated.resources.ic_gem
import energydex.composeapp.generated.resources.ic_image_placeholder
import org.jetbrains.compose.resources.painterResource

@Composable
fun EnergyDrinkItemSquared(
    energyDrink: EnergyDrink,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    Surface (
        shape = RoundedCornerShape(32.dp),
        modifier = modifier
            .border(
                width = if (isSelected) 3.dp else 0.dp,
                color = PrimaryOrange,
                shape = RoundedCornerShape(32.dp)
            )
            .clickable(onClick = onClick),
        color = SecondaryPurple
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .height(IntrinsicSize.Min)
        )
        {
            Column(
                modifier = Modifier
                    .width(IntrinsicSize.Min),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .height(110.dp)
                        .width(110.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .border(
                            width = 1.dp,
                            color = SecondaryOrange,
                            shape = RoundedCornerShape(20.dp)
                        ),
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

                Text(
                    text = energyDrink.name,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                )

            }
            Column(
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_gem),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text =if(energyDrink.rating == null)
                        {
                            "0.0"
                        } else {
                            "${energyDrink.rating}"
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp
                    )
                }
                if (energyDrink.tags.isNotEmpty()) {
                    energyDrink.tags.forEach { tag ->
                        Text(
                            text = tag.name,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = AccentWhite,
                        )
                    }
                }
                Text(
                    text = energyDrink.createdAt.toString().substring(0,10),
                    modifier = Modifier
                )
            }
        }
    }
}
