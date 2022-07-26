package com.muffarproject.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "surah")
data class SurahEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "surahNumber")
    val surahNumber: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "numberOfVerse")
    val numberOfVerse: Int,

    @ColumnInfo(name = "meaning")
    val meaning: String,

    @ColumnInfo(name = "asma")
    val asma: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean = false
)