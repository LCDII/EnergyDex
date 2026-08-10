package com.example.energydex.presentation.energydrink.energydrink_update

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.ErrorRed
import com.example.energydex.core.presentation.PrimaryOrange
import com.example.energydex.core.presentation.PrimaryPurple
import com.example.energydex.core.presentation.SecondaryOrange
import com.example.energydex.core.presentation.SecondaryPurple
import com.example.energydex.presentation.energydrink.energydrink_add.components.EnergyDrinkDescriptionTextField
import com.example.energydex.presentation.energydrink.energydrink_add.components.EnergyDrinkNameTextField
import com.example.energydex.presentation.energydrink.energydrink_add.components.EnergyDrinkRatingTextField
import com.example.energydex.presentation.energydrink.image.ImagePickerSource
import com.example.energydex.presentation.energydrink.image.PlatformImagePicker
import com.example.energydex.presentation.tag.components.TagSelector
import energydex.composeapp.generated.resources.Res
import energydex.composeapp.generated.resources.ic_image_placeholder
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
                if (action is EnergyDrinkUpdateAction.OnBackClick) {
                    onBackClick()
                }
                viewModel.onAction(action)
            }
        )
    }
}

@Composable
private fun EnergyDrinkUpdateScreen(
    state: EnergyDrinkUpdateState,
    onPickImage: () -> Unit,
    onAction: (EnergyDrinkUpdateAction) -> Unit
) {
    val imageShape = RoundedCornerShape(24.dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryPurple)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { onAction(EnergyDrinkUpdateAction.OnBackClick) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = SecondaryPurple,
                    contentColor = AccentWhite
                )
            ) {
                Text("Back")
            }
            Text("Edit energy drink", color = AccentWhite, fontSize = 22.sp)
            Box(modifier = Modifier.size(72.dp))
        }

        EnergyDrinkNameTextField(
            modifier = Modifier.fillMaxWidth(),
            name = state.name,
            onNameChange = { onAction(EnergyDrinkUpdateAction.OnNameChange(it)) },
            isValid = state.isNameTextValid
        )

        Button(
            onClick = onPickImage,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = SecondaryPurple,
                contentColor = AccentWhite
            )
        ) {
            Text(if (state.imagePath == null) "Add photo" else "Change photo")
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(imageShape)
                .border(1.dp, SecondaryOrange, imageShape),
            contentAlignment = Alignment.Center
        ) {
            if (state.imagePath == null) {
                Icon(
                    painter = painterResource(Res.drawable.ic_image_placeholder),
                    contentDescription = null,
                    tint = SecondaryOrange,
                    modifier = Modifier.size(64.dp)
                )
            } else {
                AsyncImage(
                    model = state.imagePath,
                    contentDescription = state.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                    error = painterResource(Res.drawable.ic_image_placeholder)
                )
            }
        }

        if (state.imagePath != null) {
            TextButton(
                onClick = { onAction(EnergyDrinkUpdateAction.OnRemoveImage) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Remove photo", color = SecondaryOrange)
            }
        }

        EnergyDrinkDescriptionTextField(
            modifier = Modifier.fillMaxWidth(),
            description = state.description,
            onDescriptionChange = {
                onAction(EnergyDrinkUpdateAction.OnDescriptionChange(it))
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Rating from 0 to 10", color = SecondaryOrange, fontSize = 16.sp)
            EnergyDrinkRatingTextField(
                ratingText = state.ratingText,
                onRatingChange = {
                    onAction(EnergyDrinkUpdateAction.OnRatingTextChange(it))
                },
                isValid = state.isRatingTextValid
            )
        }

        if (state.availableTags.isNotEmpty()) {
            Text("Tags", color = SecondaryOrange, fontSize = 16.sp)
            TagSelector(
                availableTags = state.availableTags,
                selectedTagIds = state.selectedTagIds,
                onTagToggle = { onAction(EnergyDrinkUpdateAction.OnTagToggle(it)) }
            )
        }

        state.errorMessage?.let { error ->
            Text(error.asString(), color = ErrorRed, fontSize = 14.sp)
        }

        Button(
            onClick = { onAction(EnergyDrinkUpdateAction.OnSaveClick) },
            enabled = !state.isSaving,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryOrange,
                contentColor = AccentWhite
            )
        ) {
            Text(if (state.isSaving) "Saving..." else "Save")
        }
    }
}
