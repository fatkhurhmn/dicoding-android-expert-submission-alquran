package com.muffarproject.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListSurahResponse(

    @field:SerializedName("data")
    val surahs: List<SurahResponse>,

    @field:SerializedName("status")
    val status: String
)