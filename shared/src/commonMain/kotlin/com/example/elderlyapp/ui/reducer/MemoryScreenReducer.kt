package com.example.elderlyapp.ui.reducer

import com.arkivanov.mvikotlin.core.store.Reducer
import com.example.elderlyapp.ui.message.MemoryScreenMessage
import com.example.elderlyapp.ui.states.MemoryScreenState

class MemoryScreenReducer : Reducer<MemoryScreenState, MemoryScreenMessage> {
    override fun MemoryScreenState.reduce(msg: MemoryScreenMessage): MemoryScreenState =
        when (msg) {
            is MemoryScreenMessage.Loading -> copy(isLoading = true, error = null)
            is MemoryScreenMessage.Success -> copy(isLoading = false, memories = msg.memories)
            is MemoryScreenMessage.Failure -> copy(isLoading = false, error = msg.error)

            is MemoryScreenMessage.Inserting -> copy(isSaving = true)
            is MemoryScreenMessage.InsertSuccess -> copy(isSaving = false, tempObjectPhoto = null, tempLocalPhoto = null)
            is MemoryScreenMessage.InsertFailure -> copy(isSaving = false, error = msg.error)

            is MemoryScreenMessage.ObjectPhotoReady -> copy(tempObjectPhoto = msg.photo)
            is MemoryScreenMessage.LocalPhotoReady -> copy(tempLocalPhoto = msg.photo)

            is MemoryScreenMessage.Deleting -> copy(isDeleting = true)
            is MemoryScreenMessage.DeleteSuccess -> copy(isDeleting = false)
            is MemoryScreenMessage.DeleteFailure -> copy(isDeleting = false, error = msg.error)
        }
}