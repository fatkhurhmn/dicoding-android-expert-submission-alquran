package com.muffarproject.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class VerseResponse(

    @field:SerializedName("ar")
    val verse: String,

    @field:SerializedName("id")
    val translate: String,

    @field:SerializedName("nomor")
    val number: String,

    @field:SerializedName("tr")
    val latin: String
)