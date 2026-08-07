package com.example.energydex.presentation.energydrink.energydrink_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.ErrorRed
import com.example.energydex.core.presentation.PrimaryOrange
import com.example.energydex.core.presentation.PrimaryPurple
import com.example.energydex.core.presentation.SecondaryOrange
import com.example.energydex.core.presentation.SecondaryPurple
import energydex.composeapp.generated.resources.Res
import energydex.composeapp.generated.resources.ic_image_placeholder
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EnergyDrinkDetailScreenRoot(
    viewModel: EnergyDrinkDetailViewModel,
    onBackClick: () -> Unit,
    onUpdateClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isDeleted) {
        if (state.isDeleted) onBackClick()
    }

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

    if (state.showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = {
                onAction(EnergyDrinkDetailAction.OnDeclineDeleteClick)
            },
            title = { Text("Delete energy drink?") },
            text = { Text("This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onAction(EnergyDrinkDetailAction.OnConfirmDeleteClick)
                    }
                ) {
                    Text("Delete", color = ErrorRed)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onAction(EnergyDrinkDetailAction.OnDeclineDeleteClick)
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryPurple)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { onAction(EnergyDrinkDetailAction.OnBackClick) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = SecondaryPurple,
                    contentColor = AccentWhite
                )
            ) {
                Text("Back")
            }
            Text("Details", color = AccentWhite, fontSize = 22.sp)
            TextButton(onClick = { onAction(EnergyDrinkDetailAction.OnUpdateClick) }) {
                Text("Edit", color = SecondaryOrange)
            }
        }

        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxWidth().height(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = PrimaryOrange)
                }
            }
            state.currentDrink != null -> {
                val drink = state.currentDrink
                val imageShape = RoundedCornerShape(24.dp)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .clip(imageShape)
                        .border(1.dp, SecondaryOrange, imageShape)
                        .then(
                            if (drink.imagePath != null) {
                                Modifier.clickable { isImagePreviewVisible = true }
                            } else {
                                Modifier
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (drink.imagePath == null) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_image_placeholder),
                            contentDescription = null,
                            tint = SecondaryOrange,
                            modifier = Modifier.size(72.dp)
                        )
                    } else {
                        AsyncImage(
                            model = drink.imagePath,
                            contentDescription = drink.name,
                            modifier = Modifier.fillMaxSize(),
                            error = painterResource(Res.drawable.ic_image_placeholder)
                        )
                    }
                }

                if (isImagePreviewVisible && drink.imagePath != null) {
                    Dialog(
                        onDismissRequest = { isImagePreviewVisible = false },
                        properties = DialogProperties(usePlatformDefaultWidth = false)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(PrimaryPurple)
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

                Text(
                    text = drink.name,
                    color = AccentWhite,
                    fontSize = 28.sp
                )
                Text(
                    text = "Rating: ${drink.rating ?: 0.0}",
                    color = SecondaryOrange,
                    fontSize = 18.sp
                )

                if (drink.tags.isNotEmpty()) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        drink.tags.forEach { tag ->
                            Text(
                                text = tag.name,
                                color = AccentWhite,
                                modifier = Modifier
                                    .background(SecondaryPurple, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Text(
                    text = drink.description?.takeIf { it.isNotBlank() }
                        ?: "No description",
                    color = AccentWhite,
                    fontSize = 17.sp
                )
                Text(
                    text = "Created: ${drink.createdAt.toString().substring(0, 10)}",
                    color = SecondaryOrange,
                    fontSize = 14.sp
                )

                state.errorMessage?.let { error ->
                    Text(error.asString(), color = ErrorRed)
                }

                Button(
                    onClick = { onAction(EnergyDrinkDetailAction.OnDeleteClick) },
                    enabled = !state.isDeleting,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ErrorRed,
                        contentColor = AccentWhite
                    )
                ) {
                    Text(if (state.isDeleting) "Deleting..." else "Delete")
                }
            }
            else -> {
                Text(
                    text = state.errorMessage?.asString() ?: "Energy drink not found",
                    color = ErrorRed
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}
