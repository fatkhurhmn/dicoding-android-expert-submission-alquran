package com.muffarproject.core.data.source.local

import com.muffarproject.core.data.source.local.entity.SurahEntity
import com.muffarproject.core.data.source.local.room.SurahDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val surahDao: SurahDao) {
    fun getAllSurah(): Flow<List<SurahEntity>> = surahDao.getAllSurah()

    fun getSurahByName(query: String): Flow<List<SurahEntity>> = surahDao.getSurahByName(query)

    fun getFavoriteSurah(): Flow<List<SurahEntity>> = surahDao.getFavoriteSurah()

    suspend fun insertSurah(surah: List<SurahEntity>) = surahDao.insertSurah(surah)

    suspend fun setFavoriteSurah(surah: SurahEntity, newState: Boolean) {
        surah.isFavorite = newState
        surahDao.updateFavoriteSurah(surah)
    }
}