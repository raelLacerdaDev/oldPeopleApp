package com.example.elderlyapp.camera

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.uikit.LocalUIViewController
import com.example.elderlyapp.gallery.SharedImage
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerSourceType

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
actual class CameraManager actual constructor(private val onLaunch: () -> Unit) {
    actual fun launch() {
        onLaunch()
    }
}

@Composable
actual fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager {

    val uiViewController = LocalUIViewController.current

    val cameraController = remember { UIImagePickerController() }

    val delegate = remember {
        CameraDelegate(
            onResult = onResult,
            dismiss = { cameraController.dismissViewControllerAnimated(true, null) }
        )
    }

    return remember {
        CameraManager(
            onLaunch = {
                if (UIImagePickerController.isSourceTypeAvailable(UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera)) {
                    cameraController.sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
                    cameraController.delegate = delegate
                    uiViewController.presentViewController(cameraController, animated = true, completion = null)
                } else {
                    onResult(null)
                }
            }
        )
    }
}