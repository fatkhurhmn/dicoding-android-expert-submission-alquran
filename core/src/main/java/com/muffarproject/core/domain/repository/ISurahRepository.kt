package com.muffarproject.core.domain.repository

import com.muffarproject.core.data.Resource
import com.muffarproject.core.domain.model.Surah
import com.muffarproject.core.domain.model.Verse
import kotlinx.coroutines.flow.Flow

interface ISurahRepository {
    fun getAllSurah(): Flow<Resource<List<Surah>>>
    fun getDetailSurah(surahNumber: String): Flow<Resource<List<Verse>>>
    fun getSurahByName(query: String): Flow<Resource<List<Surah>>>
    fun getFavoriteSurah(): Flow<List<Surah>>
    suspend fun setFavoriteSurah(surah: Surah, state: Boolean)
}