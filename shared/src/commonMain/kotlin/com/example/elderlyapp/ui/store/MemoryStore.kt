package com.example.elderlyapp.ui.store

import com.arkivanov.mvikotlin.core.store.Store
import com.example.elderlyapp.ui.intents.MemoryScreenIntents
import com.example.elderlyapp.ui.states.MemoryScreenState

interface MemoryStore: Store<MemoryScreenIntents, MemoryScreenState, Nothing>