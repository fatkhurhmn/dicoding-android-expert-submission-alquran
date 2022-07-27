package com.muffarproject.core.domain.usecase

import com.muffarproject.core.data.Resource
import com.muffarproject.core.data.SurahRepository
import com.muffarproject.core.domain.model.Surah
import com.muffarproject.core.domain.model.Verse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SurahInteractor @Inject constructor(private val surahRepository: SurahRepository) :
    SurahUseCase {
    override fun getAllSurah(): Flow<Resource<List<Surah>>> = surahRepository.getAllSurah()

    override fun getFavoriteSurah(): Flow<List<Surah>> = surahRepository.getFavoriteSurah()

    override fun getDetailSurah(surahNumber: String): Flow<Resource<List<Verse>>> =
        surahRepository.getDetailSurah(surahNumber)

    override fun setFavoriteSurah(surah: Surah, state: Boolean) =
        surahRepository.setFavoriteSurah(surah, state)
}