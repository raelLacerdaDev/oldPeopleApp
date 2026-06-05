package com.example.elderlyapp.data

import com.example.elderlyapp.gallery.SharedImage

expect class ImageStorage {
    suspend fun saveImage(sharedImage: SharedImage): String?
}