package com.example.elderlyapp.ui.states

import com.example.elderlyapp.data.dto.MemoryDto

data class MemoryScreenState(
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val memories: List<MemoryDto> = emptyList(),
    val error: String? = null
)