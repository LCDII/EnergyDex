package dev.lcdii.energydex.presentation.energydrink.image

import androidx.compose.runtime.Composable

enum class ImagePickerSource {
    CAMERA,
    GALLERY
}

@Composable
expect fun PlatformImagePicker(
    source: ImagePickerSource?,
    onImageSelected: (String) -> Unit,
    onDismiss: () -> Unit
)
