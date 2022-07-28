package com.muffarproject.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muffarproject.core.data.source.local.entity.SurahEntity

@Database(entities = [SurahEntity::class], version = 1, exportSchema = false)
abstract class SurahDatabase : RoomDatabase() {
    abstract fun surahDao(): SurahDao
}