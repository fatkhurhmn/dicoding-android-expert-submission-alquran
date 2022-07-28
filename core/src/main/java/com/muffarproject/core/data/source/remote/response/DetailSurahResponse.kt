package com.muffarproject.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailSurahResponse(

	@field:SerializedName("data")
	val verses: List<VerseResponse>,

	@field:SerializedName("status")
	val status: String
)