package com.example.energydex.presentation.tag.tag_section.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.energydex.core.presentation.SecondaryPurple
import com.example.energydex.core.presentation.tagColor
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.presentation.tag.tag_section.TagCardUiModel
import energydex.composeapp.generated.resources.Res
import energydex.composeapp.generated.resources.ic_image_placeholder
import org.jetbrains.compose.resources.painterResource

@Composable
fun TagCard(
    model: TagCardUiModel,
    onTagClick: () -> Unit,
    onDrinkClick: (EnergyDrink) -> Unit,
    modifier: Modifier = Modifier
) {
    val tagColor = tagColor(model.tag.color)
    val imageShape = RoundedCornerShape(12.dp)

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onTagClick),
        shape = RoundedCornerShape(24.dp),
        color = SecondaryPurple
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(tagColor)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = model.tag.name, color = AccentWhite)
                Text(text = "${model.drinkCount} drinks", color = tagColor)
            }
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(model.drinks.take(4), key = { it.id }) { drink ->
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(imageShape)
                            .border(1.dp, tagColor, imageShape)
                            .clickable { onDrinkClick(drink) },
                        contentAlignment = Alignment.Center
                    ) {
                        if (drink.imagePath == null) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_image_placeholder),
                                contentDescription = drink.name,
                                tint = tagColor,
                                modifier = Modifier.size(30.dp)
                            )
                        } else {
                            AsyncImage(
                                model = drink.imagePath,
                                contentDescription = drink.name,
                                modifier = Modifier.size(54.dp),
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
