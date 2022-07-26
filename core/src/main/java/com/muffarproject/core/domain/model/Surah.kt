package com.muffarproject.core.domain.model

data class Surah(
    val surahNumber: String,
    val name: String,
    val numberOfVerse: Int,
    val meaning: String,
    val asma: String,
    val type: String,
    val isFavorite: Boolean = false
)