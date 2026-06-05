package com.example.elderlyapp.di

import com.example.elderlyapp.data.AppDatabase
import com.example.elderlyapp.data.DatabaseBuilder
import com.example.elderlyapp.data.ImageStorage
import com.example.elderlyapp.data.getRoomDatabase
import com.example.elderlyapp.data.repositories.MemoryRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<AppDatabase> {
        val builder = DatabaseBuilder().getDatabaseBuilder()
        getRoomDatabase(builder)
    }
    single { ImageStorage() }
}

object KoinHelper : KoinComponent {
    val memoryRepository: MemoryRepository by inject()
}