package com.muffarproject.core.domain.usecase

import com.muffarproject.core.data.Resource
import com.muffarproject.core.domain.model.Surah
import kotlinx.coroutines.flow.Flow

interface SurahUseCase {
    fun getAllSurah(): Flow<Resource<List<Surah>>>
    fun getFavoriteSurah(): Flow<List<Surah>>
    fun setFavoriteSurah(surah: Surah, state: Boolean)
}