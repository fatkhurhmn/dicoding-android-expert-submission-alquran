package com.muffarproject.core.di

import android.content.Context
import androidx.room.Room
import com.muffarproject.core.data.source.local.room.SurahDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): SurahDatabase =
        Room.databaseBuilder(
            context, SurahDatabase::class.java, "surah.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideSurahDao(surahDatabase: SurahDatabase) = surahDatabase.surahDao()
}