package com.example.elderlyapp.data

import android.content.Context
import android.graphics.Bitmap
import com.example.elderlyapp.gallery.SharedImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class AndroidImageStorage(private val context: Context) : ImageStorage {
    override suspend fun saveImage(sharedImage: SharedImage): String? {
        return withContext(Dispatchers.IO) {
            runCatching {
                val directory = context.filesDir
                val fileName = "memory_${System.currentTimeMillis()}.jpg"
                val file = File(directory, fileName)
                FileOutputStream(file).use { outputStream ->
                    sharedImage.bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                }
                file.absolutePath
            }.getOrNull()
        }
    }
}