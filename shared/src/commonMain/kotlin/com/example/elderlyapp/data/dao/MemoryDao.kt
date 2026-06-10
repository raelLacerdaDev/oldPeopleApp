package com.example.elderlyapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.elderlyapp.data.entities.Memory
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(memory: Memory) : Long

    @Query("SELECT * FROM tb_memory ORDER BY creationDate DESC")
    fun getAllMemoriesOrderedByCreationDateDesc(): Flow<List<Memory>>


    @Query("SELECT * FROM tb_memory ORDER BY creationDate")
    fun getAllMemoriesOrderedByCreationDateAsc(): Flow<List<Memory>>

    @Query("DELETE FROM tb_memory WHERE id = :id")
    suspend fun deleteById(id: Long): Int

}