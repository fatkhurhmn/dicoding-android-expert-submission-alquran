package com.muffarproject.alquran.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.muffarproject.core.data.Resource
import com.muffarproject.core.domain.model.Surah
import com.muffarproject.core.domain.model.Verse
import com.muffarproject.core.domain.usecase.SurahUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailSurahViewModel @Inject constructor(private val surahUseCase: SurahUseCase) :
    ViewModel() {
    fun getVerse(surahNumber: String): LiveData<Resource<List<Verse>>> =
        surahUseCase.getDetailSurah(surahNumber).asLiveData()

    fun setFavorite(surah: Surah, state: Boolean) =
        surahUseCase.setFavoriteSurah(surah, state).hashCode()
}