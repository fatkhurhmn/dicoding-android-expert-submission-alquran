package com.muffarproject.alquran.listsurah

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.muffarproject.core.domain.usecase.SurahUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListSurahViewModel @Inject constructor(private val surahUseCase: SurahUseCase) : ViewModel() {
    val surah = surahUseCase.getAllSurah().asLiveData()
}