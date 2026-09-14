package com.example.energydex.presentation.energydrink.energydrink_update

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.AppBackground
import com.example.energydex.core.presentation.AccentRedGradientVertical
import com.example.energydex.core.presentation.ErrorRed
import com.example.energydex.core.presentation.GlassPanelTint
import com.example.energydex.core.presentation.PrimaryOrange
import com.example.energydex.core.presentation.glassContainer
import com.example.energydex.core.presentation.glassThumb
import com.example.energydex.presentation.energydrink.image.ImagePickerSource
import com.example.energydex.presentation.energydrink.image.PlatformImagePicker
import com.example.energydex.presentation.shared.components.GlassCircleButton
import com.example.energydex.presentation.shared.components.GlassBackButton
import com.example.energydex.presentation.tag.components.TagSelector
import com.kashif_e.backdrop.Backdrop
import com.kashif_e.backdrop.backdrops.layerBackdrop
import com.kashif_e.backdrop.backdrops.rememberCanvasBackdrop
import com.kashif_e.backdrop.backdrops.rememberLayerBackdrop
import energydex.app.shared.generated.resources.Res
import energydex.app.shared.generated.resources.ic_check
import energydex.app.shared.generated.resources.ic_delete
import energydex.app.shared.generated.resources.ic_edit
import energydex.app.shared.generated.resources.ic_image_placeholder
import energydex.app.shared.generated.resources.ic_star
import org.jetbrains.compose.resources.painterResource

@Composable
fun EnergyDrinkUpdateScreenRoot(
    viewModel: EnergyDrinkUpdateViewModel,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var pickerSource by remember { mutableStateOf<ImagePickerSource?>(null) }
    var showSourceDialog by remember { mutableStateOf(false) }

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) onSaveClick()
    }
    LaunchedEffect(state.isDeleted) {
        if (state.isDeleted) onBackClick()
    }

    PlatformImagePicker(
        source = pickerSource,
        onImageSelected = { path ->
            pickerSource = null
            viewModel.onAction(EnergyDrinkUpdateAction.OnImageSelected(path))
        },
        onDismiss = { pickerSource = null }
    )

    if (showSourceDialog) {
        AlertDialog(
            onDismissRequest = { showSourceDialog = false },
            title = { Text("Choose image source") },
            text = {
                Column {
                    TextButton(onClick = {
                        showSourceDialog = false
                        pickerSource = ImagePickerSource.CAMERA
                    }) { Text("Take photo") }
                    TextButton(onClick = {
                        showSourceDialog = false
                         pickerSource = ImagePickerSource.GALLERY
                     }) { Text("Choose from gallery") }
                    if (state.imagePath != null) {
                        TextButton(onClick = {
                        showSourceDialog = false
                            viewModel.onAction(EnergyDrinkUpdateAction.OnRemoveImage)
                        }) {
                            Text("Remove photo", color = ErrorRed)
                        }
                    }
                }
            },
            confirmButton = {}
        )
    }

    if (!state.isLoading) {
        EnergyDrinkUpdateScreen(
            state = state,
            onPickImage = { showSourceDialog = true },
            onAction = { action ->
                if (action is EnergyDrinkUpdateAction.OnBackClick) onBackClick()
                viewModel.onAction(action)
            }
        )
    } else {
        Box(
            modifier = Modifier.fillMaxSize().background(AppBackground),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = PrimaryOrange)
        }
    }
}

@Composable
private fun EnergyDrinkUpdateScreen(
    state: EnergyDrinkUpdateState,
    onPickImage: () -> Unit,
    onAction: (EnergyDrinkUpdateAction) -> Unit
) {
    val detailBackdrop = rememberLayerBackdrop()
    val fieldBackdrop = rememberCanvasBackdrop { drawRect(AppBackground) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .layerBackdrop(detailBackdrop)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .statusBarsPadding()
                    .padding(top = 12.dp, start = 8.dp, end = 8.dp, bottom = 120.dp),
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
                        text = "Edit",
                        color = AccentWhite,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center
                    )
                }

            GlassTextField(
                value = state.name,
                onValueChange = { onAction(EnergyDrinkUpdateAction.OnNameChange(it)) },
                backdrop = fieldBackdrop,
                textSize = 30.sp,
                singleLine = true
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_star),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(28.dp)
                )
                Box(modifier = Modifier.width(140.dp)) {
                    GlassTextField(
                        value = state.ratingText,
                        onValueChange = { onAction(EnergyDrinkUpdateAction.OnRatingTextChange(it)) },
                        backdrop = fieldBackdrop,
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal,
                        singleLine = true,
                        textAlign = TextAlign.Center
                    )
                }
            }

            EditableImage(
                imagePath = state.imagePath,
                name = state.name,
                onPickImage = onPickImage
            )

            if (state.availableTags.isNotEmpty()) {
                TagSelector(
                    modifier = Modifier.fillMaxWidth(),
                    availableTags = state.availableTags,
                    selectedTagIds = state.selectedTagIds,
                    onTagToggle = { onAction(EnergyDrinkUpdateAction.OnTagToggle(it)) }
                )
            }

            GlassTextField(
                value = state.description,
                onValueChange = { onAction(EnergyDrinkUpdateAction.OnDescriptionChange(it)) },
                backdrop = fieldBackdrop,
                placeholder = "No description",
                singleLine = false
            )

                state.errorMessage?.let { error ->
                    Text(error.asString(), color = ErrorRed, textAlign = TextAlign.Center)
                }
            }
        }

        GlassBackButton(
            backdrop = detailBackdrop,
            onClick = { onAction(EnergyDrinkUpdateAction.OnBackClick) },
            modifier = Modifier
                .align(Alignment.TopStart)
                .statusBarsPadding()
                .padding(top = 12.dp, start = 8.dp)
        )
        GlassCircleButton(
            onClick = { onAction(EnergyDrinkUpdateAction.OnDeleteClick) },
            contentDescription = "Delete",
            icon = Res.drawable.ic_delete,
            backdrop = detailBackdrop,
                    tintBrush = AccentRedGradientVertical,
            size = 50.dp,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .statusBarsPadding()
                .padding(top = 12.dp, end = 8.dp)
        )

        GlassCircleButton(
            onClick = { onAction(EnergyDrinkUpdateAction.OnSaveClick) },
            contentDescription = "Save",
            icon = Res.drawable.ic_check,
            backdrop = detailBackdrop,
            size = 80.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        )
    }

    if (state.showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = { onAction(EnergyDrinkUpdateAction.OnDeclineDeleteClick) },
            title = { Text("Delete energy drink?") },
            text = { Text("This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = { onAction(EnergyDrinkUpdateAction.OnConfirmDeleteClick) },
                    enabled = !state.isDeleting
                ) { Text("Delete", color = ErrorRed) }
            },
            dismissButton = {
                TextButton(
                    onClick = { onAction(EnergyDrinkUpdateAction.OnDeclineDeleteClick) },
                    enabled = !state.isDeleting
                ) { Text("Cancel") }
            }
        )
    }
}

@Composable
private fun GlassTextField(
    value: String,
    onValueChange: (String) -> Unit,
    backdrop: Backdrop,
    textSize: androidx.compose.ui.unit.TextUnit = 17.sp,
    placeholder: String? = null,
    singleLine: Boolean = true,
    keyboardType: androidx.compose.ui.text.input.KeyboardType = androidx.compose.ui.text.input.KeyboardType.Text,
    textAlign: TextAlign = TextAlign.Start
) {
    val shape = if (singleLine) RoundedCornerShape(100) else RoundedCornerShape(28.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .glassContainer(
                backdrop = backdrop,
                shape = shape,
                tint = GlassPanelTint
            )
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = shape,
            singleLine = singleLine,
            minLines = if (singleLine) 1 else 1,
            maxLines = if (singleLine) 1 else Int.MAX_VALUE,
            textStyle = TextStyle(color = AccentWhite, fontSize = textSize, textAlign = textAlign),
            placeholder = placeholder?.let {
                { Text(it, color = AccentWhite.copy(alpha = 0.55f), fontSize = textSize) }
            },
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = keyboardType),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = AccentWhite,
                unfocusedTextColor = AccentWhite,
                focusedBorderColor = AccentWhite.copy(alpha = 0.45f),
                unfocusedBorderColor = AccentWhite.copy(alpha = 0.18f),
                cursorColor = AccentWhite,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
        }
}

@Composable
private fun EditableImage(
    imagePath: String?,
    name: String,
    onPickImage: () -> Unit
) {
    val imageShape = RoundedCornerShape(24.dp)
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(440.dp)
                .clip(imageShape)
                .clickable(onClick = onPickImage),
            contentAlignment = Alignment.Center
        ) {
            if (imagePath == null) {
                Icon(
                    painter = painterResource(Res.drawable.ic_image_placeholder),
                    contentDescription = null,
                    tint = PrimaryOrange,
                    modifier = Modifier.size(72.dp)
                )
            } else {
                AsyncImage(
                    model = imagePath,
                    contentDescription = name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = androidx.compose.ui.layout.ContentScale.FillHeight,
                    error = painterResource(Res.drawable.ic_image_placeholder),
                    placeholder = painterResource(Res.drawable.ic_image_placeholder)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.32f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_edit),
                    contentDescription = "Change photo",
                    tint = AccentWhite.copy(alpha = 0.82f),
                    modifier = Modifier.size(56.dp)
                )
            }
        }
    }
}
