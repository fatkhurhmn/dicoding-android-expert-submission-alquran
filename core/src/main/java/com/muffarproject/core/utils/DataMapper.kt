package com.muffarproject.core.utils

import com.muffarproject.core.data.source.local.entity.SurahEntity
import com.muffarproject.core.data.source.remote.response.SurahResponse
import com.muffarproject.core.data.source.remote.response.VerseResponse
import com.muffarproject.core.domain.model.Surah
import com.muffarproject.core.domain.model.Verse

object DataMapper {
    fun mapSurahResponsesToSurahEntities(input: List<SurahResponse>): List<SurahEntity> {
        val surahList = ArrayList<SurahEntity>()
        input.map {
            val surahEntity = SurahEntity(
                surahNumber = it.surahNumber,
                name = it.name,
                numberOfVerse = it.numberOfVerse,
                meaning = it.meaning,
                asma = it.asma,
                type = it.type,
                isFavorite = false
            )
            surahList.add(surahEntity)
        }
        return surahList
    }

    fun mapSurahEntitiesToSurah(input: List<SurahEntity>): List<Surah> {
        val surahList = ArrayList<Surah>()
        input.map {
            val surahEntity = Surah(
                surahNumber = it.surahNumber,
                name = it.name,
                numberOfVerse = it.numberOfVerse,
                meaning = it.meaning,
                asma = it.asma,
                type = it.type,
                isFavorite = it.isFavorite
            )
            surahList.add(surahEntity)
        }
        return surahList
    }

    fun mapSurahResponseToSurah(input: SurahResponse): Surah {
        return Surah(
            surahNumber = input.surahNumber,
            name = input.name,
            numberOfVerse = input.numberOfVerse,
            meaning = input.meaning,
            asma = input.asma,
            type = input.type,
            isFavorite = false
        )
    }

    fun mapSurahToSurahEntity(input: Surah) = SurahEntity(
        surahNumber = input.surahNumber,
        name = input.name,
        numberOfVerse = input.numberOfVerse,
        meaning = input.meaning,
        asma = input.asma,
        type = input.type,
        isFavorite = false
    )

    fun mapVerseResponseToVerse(input: VerseResponse) = Verse(
        arabic = input.verse,
        latin = input.latin,
        meaning = input.translate,
        number = input.number
    )
}