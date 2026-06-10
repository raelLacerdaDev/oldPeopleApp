package com.example.elderlyapp.ui.states

import com.example.elderlyapp.data.dto.MemoryDto
import com.example.elderlyapp.gallery.SharedImage

data class MemoryScreenState(
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val memories: List<MemoryDto> = emptyList(),
    val error: String? = null,
    val tempObjectPhoto: SharedImage? = null,
    val tempLocalPhoto: SharedImage? = null
)