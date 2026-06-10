package com.example.elderlyapp.utilities

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

actual fun loadImageFromPath(path: String): ImageBitmap? {
    return runCatching {
        val bitmap = BitmapFactory.decodeFile(path)
        bitmap?.asImageBitmap()
    }.getOrNull()
}