package com.example.elderlyapp.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseBuilder(private val context: Context) {
    actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath("my_room.db")
        return Room.databaseBuilder<AppDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}