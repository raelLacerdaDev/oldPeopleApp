package com.example.elderlyapp.mappers

import com.example.elderlyapp.data.dto.MemoryDto
import com.example.elderlyapp.data.entities.Memory

fun Memory.toDto() = MemoryDto(
    id = this.id,
    objectPath = this.objectPath,
    localPath = this.localPath,
    creationDate = this.creationDate
)


fun List<Memory>.toListDto() = this.map { it.toDto() }