package com.example.energydex.presentation.energydrink.image

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.FileProvider
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import java.io.File

@Composable
actual fun PlatformImagePicker(
    source: ImagePickerSource?,
    onImageSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var cameraUri by remember { mutableStateOf<android.net.Uri?>(null) }
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) onImageSelected(uri.toString()) else onDismiss()
    }
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { saved ->
        val uri = cameraUri
        cameraUri = null
        if (!saved || uri == null) {
            onDismiss()
        } else {
            onImageSelected(uri.toString())
        }
    }
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            launchCamera(context, cameraLauncher) { cameraUri = it }
        } else {
            onDismiss()
        }
    }

    LaunchedEffect(source) {
        when (source) {
            ImagePickerSource.GALLERY -> galleryLauncher.launch("image/*")
            ImagePickerSource.CAMERA -> {
                if (context.checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    launchCamera(context, cameraLauncher) { cameraUri = it }
                } else {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }
            null -> Unit
        }
    }
}

private fun launchCamera(
    context: android.content.Context,
    launcher: androidx.activity.result.ActivityResultLauncher<android.net.Uri>,
    onUriCreated: (android.net.Uri) -> Unit
) {
    val file = File.createTempFile("energy_drink_camera_", ".jpg", context.cacheDir)
    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )
    onUriCreated(uri)
    launcher.launch(uri)
}
