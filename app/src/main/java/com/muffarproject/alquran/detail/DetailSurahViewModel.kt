package com.muffarproject.alquran.detail

import androidx.lifecycle.*
import com.muffarproject.core.data.Resource
import com.muffarproject.core.domain.model.Verse
import com.muffarproject.core.domain.usecase.SurahUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailSurahViewModel @Inject constructor(private val surahUseCase: SurahUseCase) :
    ViewModel() {

    private val mSurahNumber = MutableLiveData<String>()
    val verse: LiveData<Resource<List<Verse>>> = Transformations.switchMap(mSurahNumber, ::getVerse)
    private fun getVerse(surahNumber: String) =
        surahUseCase.getDetailSurah(surahNumber).asLiveData()

    fun setSurahNumber(surahNumber: String) = apply {
        mSurahNumber.value = surahNumber
    }
}