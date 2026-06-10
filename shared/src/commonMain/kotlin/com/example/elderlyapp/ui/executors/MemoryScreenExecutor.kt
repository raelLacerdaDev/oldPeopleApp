package com.example.elderlyapp.ui.executors

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.elderlyapp.data.repositories.MemoryRepository
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
            is MemoryScreenIntents.CaptureObjectPhoto -> dispatch(MemoryScreenMessage.ObjectPhotoReady(intent.photo))
            is MemoryScreenIntents.CaptureLocalPhoto -> dispatch(MemoryScreenMessage.LocalPhotoReady(intent.photo))
            is MemoryScreenIntents.SaveMemoryBundle -> performSave()
            is MemoryScreenIntents.DeleteMemory -> performDelete(intent.id) // <- PROCESSA EXCLUSÃO
        }
    }

    private fun fetchMemories() {
        scope.launch {
            dispatch(MemoryScreenMessage.Loading)
            repository.getAllMemoriesOrderedByCreationDateDesc().collect { listDto ->
                dispatch(MemoryScreenMessage.Success(listDto))
            }
        }
    }

    private fun performSave() {
        scope.launch {
            val currentState = state()
            val obj = currentState.tempObjectPhoto
            val loc = currentState.tempLocalPhoto
            if (obj != null && loc != null) {
                dispatch(MemoryScreenMessage.Inserting)
                val success = repository.createMemoryFromPhotos(obj, loc)
                if (success) {
                    dispatch(MemoryScreenMessage.InsertSuccess)
                } else {
                    dispatch(MemoryScreenMessage.InsertFailure("Error: Cannot save photos."))
                }
            }
        }
    }

    private fun performDelete(id: Long) {
        scope.launch {
            dispatch(MemoryScreenMessage.Deleting)
            val success = repository.deleteById(id)
            if (success) {
                dispatch(MemoryScreenMessage.DeleteSuccess)
            } else {
                dispatch(MemoryScreenMessage.DeleteFailure("Error: Cannot delete item."))
            }
        }
    }
}