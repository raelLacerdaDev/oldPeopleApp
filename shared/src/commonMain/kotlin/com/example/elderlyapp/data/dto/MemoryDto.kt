package com.example.elderlyapp.data.dto

import kotlin.time.Instant

data class MemoryDto(
    val id: Long = 0L,
    val objectPath: String,
    val localPath: String,
    val creationDate: Instant
)
