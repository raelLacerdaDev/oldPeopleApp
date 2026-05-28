package com.example.elderlyapp.camera

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.elderlyapp.gallery.SharedImage

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
actual class CameraManager actual constructor(private val onLaunch: () -> Unit) {
    actual fun launch() {
        onLaunch()
    }
}

@Composable
actual fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager {
    val context = LocalContext.current
    val contentResolver = context.contentResolver
    var tempPhotoUri : Uri by rememberSaveable { mutableStateOf(Uri.EMPTY) }
    val cameraLaunch = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                val bitmap = tempPhotoUri.toBitmapFromUri(contentResolver)
                if (bitmap != null) {
                    onResult(SharedImage(bitmap = bitmap))
                } else {
                    onResult(null)
                }
            } else {
                onResult(null)
            }
        }
    )
    return remember {
        CameraManager(
            onLaunch = {
                ComposeFileProvider.getImageUri(context)?.let {
                    tempPhotoUri = it
                    cameraLaunch.launch(tempPhotoUri)
                } ?: run { onResult(null) }
            }
        )
    }
}