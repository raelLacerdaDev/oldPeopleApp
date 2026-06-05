package com.example.elderlyapp.data

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.elderlyapp.data.converters.DateTimeConverters
import com.example.elderlyapp.data.dao.MemoryDao
import com.example.elderlyapp.data.entities.Memory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(entities = [Memory::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
@TypeConverters(DateTimeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): MemoryDao
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}


expect class DatabaseBuilder {
    fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>
}


fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}