package com.example.elderlyapp.ui.intents


import com.example.elderlyapp.gallery.SharedImage

sealed interface MemoryScreenIntents {
    object LoadMemories : MemoryScreenIntents
    data class CreateMemory(val objectPhoto: SharedImage, val localPhoto: SharedImage) : MemoryScreenIntents
}

