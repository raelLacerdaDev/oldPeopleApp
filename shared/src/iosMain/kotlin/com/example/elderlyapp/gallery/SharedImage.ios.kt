package com.example.elderlyapp.gallery

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readBytes
import kotlinx.cinterop.reinterpret
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import org.jetbrains.skia.Image

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
actual class SharedImage(val uiImage: UIImage) {
    @OptIn(ExperimentalForeignApi::class)
    actual fun toImageBitmap(): ImageBitmap {
        val nsData = UIImageJPEGRepresentation(uiImage, 0.9)
            ?: throw IllegalStateException("Cannot convert UIImage for NSData")
        val byteArray = nsData.bytes?.reinterpret<ByteVar>()?.readBytes(nsData.length.toInt())
            ?: throw IllegalStateException("Cannot read Image Bytes")
        return Image.makeFromEncoded(byteArray).toComposeImageBitmap()
    }
}