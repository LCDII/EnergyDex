package com.example.energydex.presentation.energydrink.energydrink_add

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
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
import energydex.composeapp.generated.resources.add_energy_drink_title
import energydex.composeapp.generated.resources.add_image
import energydex.composeapp.generated.resources.change_image
import energydex.composeapp.generated.resources.close_hint
import energydex.composeapp.generated.resources.done
import energydex.composeapp.generated.resources.go_back
import energydex.composeapp.generated.resources.ic_energy_drink_add
import energydex.composeapp.generated.resources.ic_image_placeholder
import energydex.composeapp.generated.resources.image_source_camera
import energydex.composeapp.generated.resources.image_source_gallery
import energydex.composeapp.generated.resources.image_source_selection
import energydex.composeapp.generated.resources.rate_hint
import energydex.composeapp.generated.resources.remove_image
import energydex.composeapp.generated.resources.selected_image
import energydex.composeapp.generated.resources.tags_title
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
        if (state.isSaved) {
            onSaveClick()
        }
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
                    TextButton(
                        onClick = {
                            isImageSourceDialogVisible = false
                            pickerSource = ImagePickerSource.CAMERA
                        }
                    ) {
                        Text(stringResource(Res.string.image_source_camera))
                    }
                    TextButton(
                        onClick = {
                            isImageSourceDialogVisible = false
                            pickerSource = ImagePickerSource.GALLERY
                        }
                    ) {
                        Text(stringResource(Res.string.image_source_gallery))
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
            if (action is EnergyDrinkAddAction.OnBackClick) {
                onBackClick()
            }
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { onAction(EnergyDrinkAddAction.OnBackClick) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = SecondaryPurple,
                    contentColor = AccentWhite
                )
            ) {
                Text(stringResource(Res.string.go_back))
            }

            Text(
                text = stringResource(Res.string.add_energy_drink_title),
                color = AccentWhite,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.width(72.dp))
        }

        EnergyDrinkNameTextField(
            modifier = Modifier.fillMaxWidth(),
            name = state.name,
            onNameChange = { onAction(EnergyDrinkAddAction.OnNameChange(it)) },
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
            Text(
                if (state.imagePath == null)
                    stringResource(Res.string.add_image)
                else
                    stringResource(Res.string.change_image)
            )
        }

        val imageShape = RoundedCornerShape(24.dp)
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
                    contentDescription = stringResource(Res.string.selected_image),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                    error = painterResource(Res.drawable.ic_image_placeholder)
                )
            }
        }

        if (state.imagePath != null) {
            TextButton(
                onClick = { onAction(EnergyDrinkAddAction.OnRemoveImage) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(stringResource(Res.string.remove_image), color = SecondaryOrange)
            }
        }

        EnergyDrinkDescriptionTextField(
            modifier = Modifier.fillMaxWidth(),
            description = state.description,
            onDescriptionChange = {
                onAction(EnergyDrinkAddAction.OnDescriptionChange(it))
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(Res.string.rate_hint),
                color = SecondaryOrange,
                fontSize = 16.sp
            )

            EnergyDrinkRatingTextField(
                ratingText = state.ratingText,
                onRatingChange = {
                    onAction(EnergyDrinkAddAction.OnRatingTextChange(it))
                },
                isValid = state.isRatingTextValid
            )
        }

        if (state.availableTags.isNotEmpty()) {
            Text(stringResource(Res.string.tags_title), color = SecondaryOrange, fontSize = 16.sp)
            TagSelector(
                availableTags = state.availableTags,
                selectedTagIds = state.selectedTagIds,
                onTagToggle = { onAction(EnergyDrinkAddAction.OnTagToggle(it)) }
            )
        }

        state.errorMessage?.let { errorMessage ->
            Text(
                text = errorMessage.asString(),
                color = ErrorRed,
                fontSize = 14.sp
            )
        }

        FloatingActionButton(
            onClick = {
                if (!state.isSaving) {
                    onAction(EnergyDrinkAddAction.OnSaveClick)
                }
            },
            elevation = FloatingActionButtonDefaults.elevation(4.dp),
            containerColor = PrimaryOrange,
            modifier = Modifier
                .align(Alignment.End)
                .size(56.dp),
            shape = CircleShape
        ) {
            if (state.isSaving) {
                Text("...", color = AccentWhite)//TODO change animation
            } else {
                Icon(
                    painter = painterResource(Res.drawable.ic_energy_drink_add),
                    contentDescription = stringResource(Res.string.done),
                    tint = AccentWhite,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
