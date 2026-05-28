package com.example.elderlyapp.camera

import androidx.compose.runtime.Composable
import com.example.elderlyapp.gallery.SharedImage

@Composable
expect fun rememberCameraManager(onResult: (SharedImage?) -> Unit) : CameraManager
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class CameraManager(onLaunch: () -> Unit) {
    fun launch()
}