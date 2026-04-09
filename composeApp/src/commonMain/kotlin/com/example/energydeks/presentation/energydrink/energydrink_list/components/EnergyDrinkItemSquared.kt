package com.example.energydeks.presentation.energydrink.energydrink_list.components

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.energydeks.core.presentation.TestColor
import com.example.energydeks.domain.energydrink.model.EnergyDrink
import com.example.energydeks.domain.tag.model.Tag
import energydeks.composeapp.generated.resources.Res
import energydeks.composeapp.generated.resources.ic_gem
import org.jetbrains.compose.resources.painterResource
import kotlin.time.Instant

@Composable
fun EnergyDrinkItemSquared(
    energyDrink: EnergyDrink,
    onClick: () -> Unit,
    modifier: Modifier  = Modifier
) {
    Surface (
        shape = RoundedCornerShape(32.dp),
        modifier = modifier
            .clickable(onClick = onClick),
        color = TestColor
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
                        .width(110.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Image"
                    )
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
                        text ="${energyDrink.rating}",
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
                            color = Color.White,
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

@Preview
@Composable
fun TmpPreviewEnergyDrinkItemSquared()
{
    EnergyDrinkItemSquared(
        energyDrink = EnergyDrink(
            id = 1,
            name = "Monster Energy White",
            amount = 0,
            description=null,
            rating = 10.0,
            createdAt = Instant.parse("2006-10-05T12:00:00Z"),
            updatedAt = Instant.parse("2006-10-05T12:00:00Z"),
            imagePath = null,
            tags = listOf(
                Tag(
                    id = 0,
                    name = "Good AF",
                    color = "#000000"
                ),
                Tag(
                    id = 0,
                    name = "Chuds Drink",
                    color = "#000000"
                )
            )
        ),
        onClick = {}
    )
}