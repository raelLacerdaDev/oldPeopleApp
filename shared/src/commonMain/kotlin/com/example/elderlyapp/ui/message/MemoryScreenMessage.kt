package com.example.elderlyapp.ui.message
import com.example.elderlyapp.data.dto.MemoryDto
import com.example.elderlyapp.gallery.SharedImage

sealed interface MemoryScreenMessage {
    data object Loading : MemoryScreenMessage
    data class Success(val memories: List<MemoryDto>) : MemoryScreenMessage
    data class Failure(val error: String) : MemoryScreenMessage

    data object Inserting : MemoryScreenMessage
    data object InsertSuccess : MemoryScreenMessage
    data class InsertFailure(val error: String) : MemoryScreenMessage

    data class ObjectPhotoReady(val photo: SharedImage?) : MemoryScreenMessage
    data class LocalPhotoReady(val photo: SharedImage?) : MemoryScreenMessage

    data object Deleting : MemoryScreenMessage
    data object DeleteSuccess : MemoryScreenMessage
    data class DeleteFailure(val error: String) : MemoryScreenMessage
}