package com.muffarproject.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.muffarproject.core.domain.usecase.SurahUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class FavoriteViewModelFactory @Inject constructor(private val surahUseCase: SurahUseCase) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            FavoriteViewModel(surahUseCase) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}