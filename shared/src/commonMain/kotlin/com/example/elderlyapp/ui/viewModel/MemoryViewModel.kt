package com.example.elderlyapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.example.elderlyapp.ui.intents.MemoryScreenIntents
import com.example.elderlyapp.ui.states.MemoryScreenState
import com.example.elderlyapp.ui.store.MemoryStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MemoryViewModel(
    private val store: MemoryStore
) : ViewModel() {

    val state: StateFlow<MemoryScreenState> = store.states.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MemoryScreenState()
    )

    init {
        store.accept(MemoryScreenIntents.LoadMemories)
    }

    fun onIntent(intent: MemoryScreenIntents) {
        store.accept(intent)
    }

    override fun onCleared() {
        super.onCleared()
        store.dispose()
    }
}