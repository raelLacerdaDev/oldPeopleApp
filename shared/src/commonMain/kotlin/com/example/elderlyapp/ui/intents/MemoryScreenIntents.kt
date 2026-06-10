package com.example.elderlyapp.ui.intents


import com.example.elderlyapp.gallery.SharedImage

sealed interface MemoryScreenIntents {
    data object LoadMemories : MemoryScreenIntents
    data class CaptureObjectPhoto(val photo: SharedImage?) : MemoryScreenIntents
    data class CaptureLocalPhoto(val photo: SharedImage?) : MemoryScreenIntents
    data object SaveMemoryBundle : MemoryScreenIntents
    data class DeleteMemory(val id: Long) : MemoryScreenIntents // <- NOVA INTENT
}

