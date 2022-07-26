package com.muffarproject.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "surah")
data class SurahEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "surahNumber")
    var surahNumber: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "numberOfVerse")
    var numberOfVerse: Int,

    @ColumnInfo(name = "meaning")
    var meaning: String,

    @ColumnInfo(name = "asma")
    var asma: String,

    @ColumnInfo(name = "type")
    var type: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)