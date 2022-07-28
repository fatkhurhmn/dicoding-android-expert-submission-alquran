package com.muffarproject.alquran.di

import com.muffarproject.core.domain.usecase.SurahInteractor
import com.muffarproject.core.domain.usecase.SurahUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideSurahUseCase(surahInteractor: SurahInteractor): SurahUseCase
}