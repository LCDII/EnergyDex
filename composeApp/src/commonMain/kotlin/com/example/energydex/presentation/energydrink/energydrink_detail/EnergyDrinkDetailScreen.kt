package com.example.energydex.presentation.energydrink.energydrink_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.AppBackground
import com.example.energydex.core.presentation.ErrorRed
import com.example.energydex.core.presentation.GlassPanelTint
import com.example.energydex.core.presentation.PrimaryOrange
import com.example.energydex.core.presentation.TagPurpleColor
import com.example.energydex.core.presentation.glassContainer
import com.example.energydex.core.presentation.ratingStarTint
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.presentation.shared.components.GlassCircleButton
import com.example.energydex.presentation.shared.components.GlassBackButton
import com.example.energydex.presentation.shared.components.TagChip
import com.kashif_e.backdrop.backdrops.layerBackdrop
import com.kashif_e.backdrop.backdrops.rememberCanvasBackdrop
import com.kashif_e.backdrop.backdrops.rememberLayerBackdrop
import energydex.composeapp.generated.resources.Res
import energydex.composeapp.generated.resources.ic_arrow_back
import energydex.composeapp.generated.resources.ic_edit
import energydex.composeapp.generated.resources.ic_image_placeholder
import energydex.composeapp.generated.resources.ic_star
import org.jetbrains.compose.resources.painterResource

@Composable
fun EnergyDrinkDetailScreenRoot(
    viewModel: EnergyDrinkDetailViewModel,
    onBackClick: () -> Unit,
    onUpdateClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    EnergyDrinkDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                EnergyDrinkDetailAction.OnBackClick -> onBackClick()
                EnergyDrinkDetailAction.OnUpdateClick -> onUpdateClick()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun EnergyDrinkDetailScreen(
    state: EnergyDrinkDetailState,
    onAction: (EnergyDrinkDetailAction) -> Unit
) {
    var isImagePreviewVisible by remember { mutableStateOf(false) }
    val detailBackdrop = rememberLayerBackdrop()
    val tagBackdrop = rememberCanvasBackdrop { drawRect(AppBackground) }
    val topBarShape = RoundedCornerShape(50)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .layerBackdrop(detailBackdrop)
                .statusBarsPadding()
                .padding(top = 12.dp, start = 20.dp, end = 20.dp, bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Details",
                    color = AccentWhite,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )
            }

            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = PrimaryOrange)
                    }
                }

                state.currentDrink != null -> {
                    val drink = state.currentDrink
                    DetailContent(
                        drink = drink,
                        tagBackdrop = tagBackdrop,
                        onImageClick = { isImagePreviewVisible = true }
                    )

                    if (isImagePreviewVisible && drink.imagePath != null) {
                        Dialog(
                            onDismissRequest = { isImagePreviewVisible = false },
                            properties = DialogProperties(usePlatformDefaultWidth = false)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(AppBackground)
                                    .clickable { isImagePreviewVisible = false },
                                contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    model = drink.imagePath,
                                    contentDescription = drink.name,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = androidx.compose.ui.layout.ContentScale.Fit,
                                    error = painterResource(Res.drawable.ic_image_placeholder)
                                )
                            }
                        }
                    }

                    state.errorMessage?.let { error ->
                        Text(error.asString(), color = ErrorRed)
                    }
                }

                else -> {
                    Text(
                        text = state.errorMessage?.asString() ?: "Energy drink not found",
                        color = ErrorRed,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        GlassBackButton(
            backdrop = detailBackdrop,
            onClick = { onAction(EnergyDrinkDetailAction.OnBackClick) },
            modifier = Modifier
                .align(Alignment.TopStart)
                .statusBarsPadding()
                .padding(top = 12.dp, start = 8.dp)
        )

        GlassCircleButton(
            onClick = { onAction(EnergyDrinkDetailAction.OnUpdateClick) },
            contentDescription = "Edit",
            icon = Res.drawable.ic_edit,
            backdrop = detailBackdrop,
            size = 80.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        )
    }
}

@Composable
private fun DetailContent(
    drink: EnergyDrink,
    tagBackdrop: com.kashif_e.backdrop.Backdrop,
    onImageClick: () -> Unit
) {
    Text(
        text = drink.name,
        color = AccentWhite,
        fontSize = 30.sp,
        textAlign = TextAlign.Center,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth()
    )
    DetailRating(drink.rating)

    val imageShape = RoundedCornerShape(24.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .clip(imageShape)
            .then(if (drink.imagePath != null) Modifier.clickable(onClick = onImageClick) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        if (drink.imagePath == null) {
            Icon(
                painter = painterResource(Res.drawable.ic_image_placeholder),
                contentDescription = null,
                tint = PrimaryOrange,
                modifier = Modifier.size(72.dp)
            )
        } else {
            AsyncImage(
                model = drink.imagePath,
                contentDescription = drink.name,
                modifier = Modifier.fillMaxSize(),
                error = painterResource(Res.drawable.ic_image_placeholder),
                placeholder = painterResource(Res.drawable.ic_image_placeholder)
            )
        }
    }

    if (drink.tags.isNotEmpty()) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            drink.tags.forEach { tag ->
                TagChip(
                    tag = tag,
                    backdrop = tagBackdrop,
                    fontSize = 16.sp,
                    horizontalPadding = 12.dp,
                    verticalPadding = 3.dp,
                    modifier = Modifier
                        .widthIn(min = 64.dp)
                        .height(50.dp)
                )
            }
        }
    }

    drink.description
        ?.takeIf { it.isNotBlank() }
        ?.let { description ->
            Text(
                text = description,
                color = AccentWhite,
                fontSize = 17.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
}

@Composable
private fun DetailRating(rating: Double?) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_star),
            contentDescription = null,
            tint = if (rating != null && rating >= 10.0) TagPurpleColor else ratingStarTint(rating),
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = rating?.toString() ?: "0.0",
            color = AccentWhite,
            fontSize = 24.sp,
            style = MaterialTheme.typography.titleLarge
        )
    }
}
