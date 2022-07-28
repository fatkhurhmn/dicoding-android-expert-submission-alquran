package com.muffarproject.core.di

import com.muffarproject.core.data.SurahRepository
import com.muffarproject.core.domain.repository.ISurahRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [DatabaseModule::class, NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(surahRepository: SurahRepository): ISurahRepository
}