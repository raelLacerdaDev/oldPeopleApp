package com.example.elderlyapp.gallery

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
actual class SharedImage(val bitmap: Bitmap) {
    actual fun toImageBitmap(): ImageBitmap {
        return bitmap.asImageBitmap()
    }
}