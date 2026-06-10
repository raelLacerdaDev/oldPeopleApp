package com.example.elderlyapp.data

import com.example.elderlyapp.gallery.SharedImage

interface ImageStorage {
    suspend fun saveImage(sharedImage: SharedImage): String?
}