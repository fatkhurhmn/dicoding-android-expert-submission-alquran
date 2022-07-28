package com.muffarproject.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.muffarproject.core.domain.usecase.SurahUseCase

class FavoriteViewModel (surahUseCase: SurahUseCase) : ViewModel() {
    val favorites = surahUseCase.getFavoriteSurah().asLiveData()
}