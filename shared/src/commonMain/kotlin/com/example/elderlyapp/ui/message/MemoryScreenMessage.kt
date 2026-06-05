package com.example.elderlyapp.ui.message
import com.example.elderlyapp.data.dto.MemoryDto

interface MemoryScreenMessage {
    object Loading : MemoryScreenMessage
    data class Success(val memories: List<MemoryDto>) : MemoryScreenMessage
    data class Failure(val error: String) : MemoryScreenMessage
    object Inserting : MemoryScreenMessage
    object InsertSuccess : MemoryScreenMessage
    data class InsertFailure(val error: String) : MemoryScreenMessage
}