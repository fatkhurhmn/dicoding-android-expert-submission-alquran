package com.muffarproject.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SurahResponse(

    @field:SerializedName("nama")
    val name: String,

    @field:SerializedName("ayat")
    val numberOfVerse: Int,

    @field:SerializedName("arti")
    val meaning: String,

    @field:SerializedName("asma")
    val asma: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("nomor")
    val surahNumber: String
)