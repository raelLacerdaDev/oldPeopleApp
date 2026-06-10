package com.example.elderlyapp.di

import com.example.elderlyapp.data.AndroidImageStorage
import com.example.elderlyapp.data.AppDatabase
import com.example.elderlyapp.data.DatabaseBuilder
import com.example.elderlyapp.data.ImageStorage
import com.example.elderlyapp.data.getRoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule = module {
    single<AppDatabase> {
        val builder = DatabaseBuilder(androidContext()).getDatabaseBuilder()
        getRoomDatabase(builder)
    }
    single<ImageStorage> { AndroidImageStorage(androidContext()) }
}