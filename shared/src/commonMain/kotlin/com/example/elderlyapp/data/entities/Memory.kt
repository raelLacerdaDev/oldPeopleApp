package com.example.elderlyapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Instant

@Entity(tableName = "tb_memory")
data class Memory(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val objectPath: String,
    val localPath: String,
    val creationDate: Instant
)
