package com.example.elderlyapp.ui.executors

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.elderlyapp.data.dto.MemoryDto
import com.example.elderlyapp.data.repositories.MemoryRepository
import com.example.elderlyapp.gallery.SharedImage
import com.example.elderlyapp.ui.intents.MemoryScreenIntents
import com.example.elderlyapp.ui.message.MemoryScreenMessage
import com.example.elderlyapp.ui.states.MemoryScreenState
import kotlinx.coroutines.launch

class MemoryScreenExecutor(
    private val repository: MemoryRepository
) : CoroutineExecutor<MemoryScreenIntents, Nothing, MemoryScreenState, MemoryScreenMessage, Nothing>() {

    override fun executeIntent(intent: MemoryScreenIntents) {
        when(intent) {
            is MemoryScreenIntents.LoadMemories -> fetchMemories()
            is MemoryScreenIntents.CreateMemory -> saveMemory(intent.objectPhoto, intent.localPhoto)
        }
    }

    private fun fetchMemories() {
        scope.launch {
            dispatch(MemoryScreenMessage.Loading)
            repository.getAllMemoriesOrderedByCreationDateDesc()
                .collect { listDto ->
                    dispatch(MemoryScreenMessage.Success(listDto))
                }
        }
    }

    private fun saveMemory(objectPhoto: SharedImage, localPhoto: SharedImage) {
        scope.launch {
            dispatch(MemoryScreenMessage.Inserting)
            val success = repository.createMemoryFromPhotos(objectPhoto,localPhoto)
            if (success) {
                dispatch(MemoryScreenMessage.InsertSuccess)
            } else {
                dispatch(MemoryScreenMessage.InsertFailure("ERROR: Cannot save!"))
            }
        }
    }
}