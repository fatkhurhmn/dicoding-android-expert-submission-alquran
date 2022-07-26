package com.muffarproject.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("data")
	val data: List<SurahResponse>,

	@field:SerializedName("status")
	val status: String
)