package com.example.elderlyapp

import android.app.Application
import com.example.elderlyapp.data.AppDatabase
import com.example.elderlyapp.data.DatabaseBuilder
import com.example.elderlyapp.data.getRoomDatabase
import com.example.elderlyapp.data.repositories.MemoryRepository
import com.example.elderlyapp.di.initKoin
import org.koin.android.ext.koin.androidContext


class ElderlyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@ElderlyApplication)
        }
    }
}