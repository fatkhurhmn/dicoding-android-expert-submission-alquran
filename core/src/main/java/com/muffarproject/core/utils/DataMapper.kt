package com.muffarproject.core.utils

import com.muffarproject.core.data.source.local.entity.SurahEntity
import com.muffarproject.core.data.source.remote.response.SurahResponse
import com.muffarproject.core.domain.model.Surah

object DataMapper {
    fun mapResponsesToEntities(input: List<SurahResponse>): List<SurahEntity> {
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

    fun mapEntitiesToDomain(input: List<SurahEntity>): List<Surah> {
        val surahList = ArrayList<Surah>()
        input.map {
            val surahEntity = Surah(
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

    fun mapDomainToEntity(input: Surah) = SurahEntity(
        surahNumber = input.surahNumber,
        name = input.name,
        numberOfVerse = input.numberOfVerse,
        meaning = input.meaning,
        asma = input.asma,
        type = input.type,
        isFavorite = false
    )
}