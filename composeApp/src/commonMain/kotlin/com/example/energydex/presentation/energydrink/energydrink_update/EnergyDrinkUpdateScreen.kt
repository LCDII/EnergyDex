package com.example.energydex.presentation.energydrink.energydrink_update

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
import com.example.energydex.core.presentation.TabGradientVertical
import com.example.energydex.core.presentation.TextOnGradient
import com.example.energydex.core.presentation.glassContainer
import com.example.energydex.core.presentation.glassThumb
import com.example.energydex.core.presentation.ratingStarTint
import com.example.energydex.core.presentation.TagPurpleColor
import com.example.energydex.presentation.energydrink.image.ImagePickerSource
import com.example.energydex.presentation.energydrink.image.PlatformImagePicker
import com.example.energydex.domain.energydrink.model.EnergyDrink
import com.example.energydex.presentation.shared.components.TagChip
import com.example.energydex.presentation.shared.components.GlassCircleButton
import com.example.energydex.presentation.shared.components.GlassBackButton
import com.example.energydex.presentation.energydrink.energydrink_update.components.EditingField
import com.kashif_e.backdrop.Backdrop
import com.kashif_e.backdrop.backdrops.layerBackdrop
import com.kashif_e.backdrop.backdrops.rememberCanvasBackdrop
import com.kashif_e.backdrop.backdrops.rememberLayerBackdrop
import com.example.energydex.core.presentation.AccentRedGradient
import energydex.composeapp.generated.resources.Res
import energydex.composeapp.generated.resources.ic_arrow_back
import energydex.composeapp.generated.resources.ic_check
import energydex.composeapp.generated.resources.ic_close
import energydex.composeapp.generated.resources.ic_delete
import energydex.composeapp.generated.resources.ic_edit
import energydex.composeapp.generated.resources.ic_image_placeholder
import energydex.composeapp.generated.resources.ic_star
import org.jetbrains.compose.resources.DrawableResource
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
    var editingField by remember { mutableStateOf<EditingField?>(null) }
    var nameDraft by remember { mutableStateOf("") }
    var ratingDraft by remember { mutableStateOf("") }
    var descriptionDraft by remember { mutableStateOf("") }
    val detailBackdrop = rememberLayerBackdrop()
    val tagBackdrop = rememberCanvasBackdrop { drawRect(AppBackground) }
    val topBarShape = RoundedCornerShape(50)

    LaunchedEffect(state.name, state.ratingText, state.description) {
        if (editingField == null) {
            nameDraft = state.name
            ratingDraft = state.ratingText
            descriptionDraft = state.description
        }
    }

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
                    text = "Edit",
                    color = AccentWhite,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )
            }

            if (editingField == EditingField.NAME) {
                EditableField(
                    value = nameDraft,
                    onValueChange = { nameDraft = it },
                    onConfirm = {
                        onAction(EnergyDrinkUpdateAction.OnNameChange(nameDraft))
                        editingField = null
                    },
                    onCancel = {
                        nameDraft = state.name
                        editingField = null
                    }
                )
            } else {
                ReadOnlyValue(
                    value = state.name,
                    onEdit = {
                        nameDraft = state.name
                        editingField = EditingField.NAME
                    },
                    textSize = 30.sp
                )
            }

            if (editingField == EditingField.RATING) {
                EditableField(
                    value = ratingDraft,
                    onValueChange = { ratingDraft = it },
                    onConfirm = {
                        onAction(EnergyDrinkUpdateAction.OnRatingTextChange(ratingDraft))
                        editingField = null
                    },
                    onCancel = {
                        ratingDraft = state.ratingText
                        editingField = null
                    },
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal
                )
            } else {
                DetailRating(
                    rating = state.rating,
                    onEdit = {
                        ratingDraft = state.ratingText
                        editingField = EditingField.RATING
                    }
                )
            }

            EditableImage(
                imagePath = state.imagePath,
                name = state.name,
                onPickImage = onPickImage,
                onRemoveImage = { onAction(EnergyDrinkUpdateAction.OnRemoveImage) }
            )

            val selectedTags = state.availableTags.filter { it.id in state.selectedTagIds }
            if (selectedTags.isNotEmpty()) {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    selectedTags.forEach { tag ->
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

            if (editingField == EditingField.DESCRIPTION) {
                EditableField(
                    value = descriptionDraft,
                    onValueChange = { descriptionDraft = it },
                    onConfirm = {
                        onAction(EnergyDrinkUpdateAction.OnDescriptionChange(descriptionDraft))
                        editingField = null
                    },
                    onCancel = {
                        descriptionDraft = state.description
                        editingField = null
                    },
                    singleLine = false
                )
            } else {
                ReadOnlyValue(
                    value = state.description,
                    onEdit = {
                        descriptionDraft = state.description
                        editingField = EditingField.DESCRIPTION
                    },
                    textSize = 17.sp,
                    placeholder = "No description"
                )
            }

            state.errorMessage?.let { error ->
                Text(error.asString(), color = ErrorRed, textAlign = TextAlign.Center)
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
private fun ReadOnlyValue(
    value: String,
    onEdit: () -> Unit,
    textSize: androidx.compose.ui.unit.TextUnit,
    placeholder: String? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = value.ifBlank { placeholder.orEmpty() },
            color = AccentWhite,
            fontSize = textSize,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f, fill = false),
            maxLines = if (textSize.value > 20f) 2 else 6,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.width(4.dp))
        EditIconButton(onClick = onEdit)
    }
}

@Composable
private fun EditableField(
    value: String,
    onValueChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    singleLine: Boolean = true,
    keyboardType: androidx.compose.ui.text.input.KeyboardType = androidx.compose.ui.text.input.KeyboardType.Text
) {
    val focusRequester = remember { FocusRequester() }
    var fieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = value,
                selection = TextRange(value.length)
            )
        )
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        OutlinedTextField(
            value = fieldValue,
            onValueChange = {
                fieldValue = it
                onValueChange(it.text)
            },
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester),
            singleLine = singleLine,
            maxLines = if (singleLine) 1 else 5,
            textStyle = TextStyle(color = AccentWhite, fontSize = 17.sp),
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = keyboardType),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = AccentWhite,
                unfocusedTextColor = AccentWhite,
                focusedBorderColor = AccentWhite,
                unfocusedBorderColor = AccentWhite,
                cursorColor = AccentWhite,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            FieldActionButton(
                icon = Res.drawable.ic_check,
                tintBrush = TabGradientVertical,
                onClick = onConfirm
            )
            FieldActionButton(
                icon = Res.drawable.ic_close,
                tintBrush = AccentRedGradientVertical,
                onClick = onCancel
            )
        }
    }
}

@Composable
private fun DetailRating(
    rating: Double?,
    onEdit: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
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
                fontSize = 24.sp
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        EditIconButton(onClick = onEdit)
    }
}

@Composable
private fun EditIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(36.dp)
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_edit),
            contentDescription = "Edit",
            tint = AccentWhite,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun EditableImage(
    imagePath: String?,
    name: String,
    onPickImage: () -> Unit,
    onRemoveImage: () -> Unit
) {
    val imageShape = RoundedCornerShape(24.dp)
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
            .height(420.dp)
                .clip(imageShape),
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
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextButton(onClick = onPickImage) {
                Text(if (imagePath == null) "Add photo" else "Change photo", color = AccentWhite)
            }
            if (imagePath != null) {
                TextButton(onClick = onRemoveImage) {
                    Text("Remove photo", color = ErrorRed)
                }
            }
        }
    }
}

@Composable
private fun FieldActionButton(
    icon: DrawableResource,
    tintBrush: Brush,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(tintBrush),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = TextOnGradient,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
