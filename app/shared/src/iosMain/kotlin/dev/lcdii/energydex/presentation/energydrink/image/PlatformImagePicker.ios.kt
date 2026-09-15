package dev.lcdii.energydex.presentation.energydrink.image

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import platform.Foundation.NSDate
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.NSURL
import platform.Foundation.timeIntervalSince1970
import platform.Foundation.writeToFile
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.UIKit.UIViewController
import platform.darwin.NSObject

@Composable
actual fun PlatformImagePicker(
    source: ImagePickerSource?,
    onImageSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    if (source == null) return

    val delegate = remember(source) {
        object : NSObject(), UIImagePickerControllerDelegateProtocol,
            UINavigationControllerDelegateProtocol {
            override fun imagePickerController(
                picker: UIImagePickerController,
                didFinishPickingMediaWithInfo: Map<Any?, *>
            ) {
                val image = didFinishPickingMediaWithInfo[
                    "UIImagePickerControllerOriginalImage"
                ] as? UIImage
                val data = image?.let { UIImageJPEGRepresentation(it, 0.9) }
                if (data == null) {
                    picker.dismissViewControllerAnimated(true, completion = onDismiss)
                    return
                }

                val path = NSTemporaryDirectory() + "energy_drink_${NSDate().timeIntervalSince1970}.jpg"
                if (data.writeToFile(path, atomically = true)) {
                    picker.dismissViewControllerAnimated(true) {
                        onImageSelected(NSURL.fileURLWithPath(path).absoluteString ?: path)
                    }
                } else {
                    picker.dismissViewControllerAnimated(true, completion = onDismiss)
                }
            }

            override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
                picker.dismissViewControllerAnimated(true, completion = onDismiss)
            }
        }
    }

    DisposableEffect(source) {
        val picker = UIImagePickerController()
        picker.delegate = delegate
        picker.sourceType = when (source) {
            ImagePickerSource.CAMERA ->
                platform.UIKit.UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
            ImagePickerSource.GALLERY ->
                platform.UIKit.UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary
        }
        topViewController()?.presentViewController(
            viewControllerToPresent = picker,
            animated = true,
            completion = null
        )

        onDispose { }
    }
}

private fun topViewController(): UIViewController? =
    UIApplication.sharedApplication.keyWindow?.rootViewController
