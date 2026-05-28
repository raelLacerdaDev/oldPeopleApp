package com.example.elderlyapp.gallery

import androidx.compose.ui.graphics.ImageBitmap

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class SharedImage {
    fun toImageBitmap() : ImageBitmap
}