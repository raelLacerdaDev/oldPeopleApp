package com.example.elderlyapp.camera

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import android.net.Uri

fun Uri.toBitmapFromUri(contentResolver: ContentResolver): Bitmap? = runCatching {

    val inputStreamForBitmap = contentResolver.openInputStream(this)
    val bitmap = inputStreamForBitmap?.use {
        BitmapFactory.decodeStream(it)
    } ?: return@runCatching null

    val inputStreamForExif = contentResolver.openInputStream(this)
    val exif = inputStreamForExif?.use {
        ExifInterface(it)
    }

    val orientation = exif?.getAttributeInt(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_NORMAL
    ) ?: ExifInterface.ORIENTATION_NORMAL

    val degrees = when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> 90f
        ExifInterface.ORIENTATION_ROTATE_180 -> 180f
        ExifInterface.ORIENTATION_ROTATE_270 -> 270f
        else -> 0f
    }

    if (degrees != 0f) {
        val matrix = Matrix().apply { postRotate(degrees) }
        Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    } else {
        bitmap
    }
}.getOrNull()