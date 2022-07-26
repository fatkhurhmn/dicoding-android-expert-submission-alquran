package com.muffarproject.core.domain.usecase

import com.muffarproject.core.data.Resource
import com.muffarproject.core.data.SurahRepository
import com.muffarproject.core.domain.model.Surah
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SurahInteractor @Inject constructor(private val surahRepository: SurahRepository) :
    SurahUseCase {
    override fun getAllSurah(): Flow<Resource<List<Surah>>> = surahRepository.getAllSurah()

    override fun getFavoriteSurah(): Flow<List<Surah>> = surahRepository.getFavoriteSurah()

    override fun setFavoriteSurah(surah: Surah, state: Boolean) =
        surahRepository.setSurahFavorite(surah, state)
}