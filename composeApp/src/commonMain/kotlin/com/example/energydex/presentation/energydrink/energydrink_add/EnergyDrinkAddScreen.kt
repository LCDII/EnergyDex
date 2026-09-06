package com.example.energydex.presentation.energydrink.energydrink_add

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.AppBackground
import com.example.energydex.core.presentation.ErrorRed
import com.example.energydex.core.presentation.GlassPanelTint
import com.example.energydex.core.presentation.PrimaryOrange
import com.example.energydex.core.presentation.glassContainer
import com.example.energydex.presentation.energydrink.image.ImagePickerSource
import com.example.energydex.presentation.energydrink.image.PlatformImagePicker
import com.example.energydex.presentation.shared.components.GlassBackButton
import com.example.energydex.presentation.shared.components.GlassCircleButton
import com.example.energydex.presentation.tag.components.TagSelector
import com.kashif_e.backdrop.Backdrop
import com.kashif_e.backdrop.backdrops.layerBackdrop
import com.kashif_e.backdrop.backdrops.rememberCanvasBackdrop
import com.kashif_e.backdrop.backdrops.rememberLayerBackdrop
import energydex.composeapp.generated.resources.Res
import energydex.composeapp.generated.resources.energy_drink_description
import energydex.composeapp.generated.resources.energy_drink_name
import energydex.composeapp.generated.resources.energy_drink_name_required
import energydex.composeapp.generated.resources.ic_check
import energydex.composeapp.generated.resources.ic_edit
import energydex.composeapp.generated.resources.ic_image_placeholder
import energydex.composeapp.generated.resources.ic_star
import energydex.composeapp.generated.resources.image_source_camera
import energydex.composeapp.generated.resources.image_source_gallery
import energydex.composeapp.generated.resources.image_source_selection
import energydex.composeapp.generated.resources.remove_image
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EnergyDrinkAddScreenRoot(
    viewModel: EnergyDrinkAddViewModel = koinViewModel(),
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var pickerSource by remember { mutableStateOf<ImagePickerSource?>(null) }
    var isImageSourceDialogVisible by remember { mutableStateOf(false) }

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) onSaveClick()
    }

    PlatformImagePicker(
        source = pickerSource,
        onImageSelected = { path ->
            pickerSource = null
            viewModel.onAction(EnergyDrinkAddAction.OnImageSelected(path))
        },
        onDismiss = { pickerSource = null }
    )

    if (isImageSourceDialogVisible) {
        AlertDialog(
            onDismissRequest = { isImageSourceDialogVisible = false },
            title = { Text(stringResource(Res.string.image_source_selection)) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    TextButton(onClick = {
                        isImageSourceDialogVisible = false
                        pickerSource = ImagePickerSource.CAMERA
                    }) { Text(stringResource(Res.string.image_source_camera)) }
                    TextButton(onClick = {
                        isImageSourceDialogVisible = false
                        pickerSource = ImagePickerSource.GALLERY
                    }) { Text(stringResource(Res.string.image_source_gallery)) }
                    if (state.imagePath != null) {
                        TextButton(onClick = {
                            isImageSourceDialogVisible = false
                            viewModel.onAction(EnergyDrinkAddAction.OnRemoveImage)
                        }) {
                            Text(stringResource(Res.string.remove_image), color = ErrorRed)
                        }
                    }
                }
            },
            confirmButton = {}
        )
    }

    EnergyDrinkAddScreen(
        state = state,
        onPickImage = { isImageSourceDialogVisible = true },
        onAction = { action ->
            if (action is EnergyDrinkAddAction.OnBackClick) onBackClick()
            viewModel.onAction(action)
        }
    )
}

@Composable
fun EnergyDrinkAddScreen(
    state: EnergyDrinkAddState,
    onPickImage: () -> Unit,
    onAction: (EnergyDrinkAddAction) -> Unit
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
                Box(modifier = Modifier.fillMaxWidth().height(50.dp))

                GlassTextField(
                    value = state.name,
                    onValueChange = { onAction(EnergyDrinkAddAction.OnNameChange(it)) },
                    backdrop = fieldBackdrop,
                    placeholder = stringResource(Res.string.energy_drink_name),
                    textSize = 30.sp,
                    isError = !state.isNameTextValid
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
                            onValueChange = {
                                onAction(EnergyDrinkAddAction.OnRatingTextChange(it))
                            },
                            backdrop = fieldBackdrop,
                            placeholder = "0.0",
                            keyboardType = KeyboardType.Decimal,
                            isError = !state.isRatingTextValid,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                AddEditableImage(
                    imagePath = state.imagePath,
                    onPickImage = onPickImage
                )

                if (state.availableTags.isNotEmpty()) {
                    TagSelector(
                        modifier = Modifier.fillMaxWidth(),
                        availableTags = state.availableTags,
                        selectedTagIds = state.selectedTagIds,
                        onTagToggle = { onAction(EnergyDrinkAddAction.OnTagToggle(it)) }
                    )
                }

                GlassTextField(
                    value = state.description,
                    onValueChange = {
                        onAction(EnergyDrinkAddAction.OnDescriptionChange(it))
                    },
                    backdrop = fieldBackdrop,
                    placeholder = stringResource(Res.string.energy_drink_description),
                    singleLine = false
                )

                state.errorMessage?.let { errorMessage ->
                    Text(
                        text = errorMessage.asString(),
                        color = ErrorRed,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        GlassBackButton(
            backdrop = detailBackdrop,
            onClick = { onAction(EnergyDrinkAddAction.OnBackClick) },
            modifier = Modifier
                .align(Alignment.TopStart)
                .statusBarsPadding()
                .padding(top = 12.dp, start = 8.dp)
        )

        GlassCircleButton(
            onClick = {
                if (!state.isSaving) onAction(EnergyDrinkAddAction.OnSaveClick)
            },
            contentDescription = "Save",
            icon = Res.drawable.ic_check,
            backdrop = detailBackdrop,
            size = 80.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        )
    }
}

@Composable
private fun GlassTextField(
    value: String,
    onValueChange: (String) -> Unit,
    backdrop: Backdrop,
    placeholder: String,
    textSize: TextUnit = 17.sp,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    textAlign: TextAlign = TextAlign.Start,
    isError: Boolean = false
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
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = shape,
            singleLine = singleLine,
            minLines = 1,
            maxLines = if (singleLine) 1 else Int.MAX_VALUE,
            isError = isError,
            placeholder = { Text(placeholder, color = AccentWhite.copy(alpha = 0.55f)) },
            textStyle = TextStyle(
                color = AccentWhite,
                fontSize = textSize,
                textAlign = textAlign
            ),
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                keyboardType = keyboardType
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = AccentWhite,
                unfocusedTextColor = AccentWhite,
                cursorColor = AccentWhite,
                errorCursorColor = ErrorRed,
                focusedBorderColor = AccentWhite.copy(alpha = 0.45f),
                unfocusedBorderColor = AccentWhite.copy(alpha = 0.18f),
                errorBorderColor = ErrorRed,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
    }
}

@Composable
private fun AddEditableImage(
    imagePath: String?,
    onPickImage: () -> Unit
) {
    val imageShape = RoundedCornerShape(24.dp)
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
                contentDescription = null,
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
