package com.muffarproject.core.data.source.local.room

import androidx.room.*
import com.muffarproject.core.data.source.local.entity.SurahEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SurahDao {

    @Query("SELECT * FROM surah")
    fun getAllSurah(): Flow<List<SurahEntity>>

    @Query("SELECT * FROM surah WHERE isFavorite = 1")
    fun getFavoriteSurah(): Flow<List<SurahEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSurah(surah: List<SurahEntity>)

    @Update
    fun updateFavoriteSurah(surah: SurahEntity)
}