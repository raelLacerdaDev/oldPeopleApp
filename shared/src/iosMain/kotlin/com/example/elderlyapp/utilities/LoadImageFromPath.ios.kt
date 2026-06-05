package com.example.elderlyapp.utilities

import androidx.compose.ui.graphics.ImageBitmap
import com.example.elderlyapp.gallery.SharedImage
import platform.UIKit.UIImage

actual fun loadImageFromPath(path: String): ImageBitmap? {
    val uiImage = UIImage.imageWithContentsOfFile(path) ?: return null
    return SharedImage(uiImage).toImageBitmap()
}