package com.example.elderlyapp.data.repositories


import com.example.elderlyapp.data.ImageStorage
import com.example.elderlyapp.data.dao.MemoryDao
import com.example.elderlyapp.data.entities.Memory
import com.example.elderlyapp.data.dto.MemoryDto
import com.example.elderlyapp.gallery.SharedImage
import com.example.elderlyapp.mappers.toDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.time.Clock

class MemoryRepository(
    private val memoryDao: MemoryDao,
    private val imageStorage: ImageStorage,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun insert(memoryDto: MemoryDto) : Boolean {
        return withContext(dispatcher) {
            val item = Memory(
                id = memoryDto.id,
                objectPath = memoryDto.objectPath,
                localPath = memoryDto.localPath,
                creationDate = memoryDto.creationDate,
            )
            memoryDao.insert(item) > 0
        }
    }

    suspend fun createMemoryFromPhotos(objectPhoto: SharedImage, localPhoto: SharedImage): Boolean {
        return withContext(dispatcher) {
            val objPath = imageStorage.saveImage(objectPhoto) ?: return@withContext false
            val locPath = imageStorage.saveImage(localPhoto) ?: return@withContext false
            val newMemoryDto = MemoryDto(
                id = 0L,
                objectPath = objPath,
                localPath = locPath,
                creationDate = Clock.System.now()
            )
            insert(newMemoryDto)
        }
    }

    fun getAllMemoriesOrderedByCreationDateDesc() = memoryDao.getAllMemoriesOrderedByCreationDateDesc().map { list ->
        list.map {
            it.toDto()
        }
    }.flowOn(dispatcher)

    fun getAllMemoriesOrderedByCreationDateAsc() = memoryDao.getAllMemoriesOrderedByCreationDateAsc().map { list ->
        list.map {
            it.toDto()
        }
    }.flowOn(dispatcher)

}