package dev.lcdii.energydex.presentation.energydrink.image

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
actual fun PlatformImagePicker(
    source: ImagePickerSource?,
    onImageSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    // Browser image picking is not wired yet; keep the shared screen usable.
    LaunchedEffect(source) {
        if (source != null) onDismiss()
    }
}
