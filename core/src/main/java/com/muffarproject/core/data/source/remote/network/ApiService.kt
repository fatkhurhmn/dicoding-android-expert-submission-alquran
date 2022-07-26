package com.muffarproject.core.data.source.remote.network

import com.muffarproject.core.data.source.remote.response.DetailSurahResponse
import com.muffarproject.core.data.source.remote.response.ListSurahResponse
import com.muffarproject.core.data.source.remote.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("surah")
    suspend fun getListSurah(): ListSurahResponse

    @GET("surah/{surah_number}")
    suspend fun getDetailSurah(
        @Path("surah_number") surah_number: String
    ): DetailSurahResponse

    @GET("surah/search/{surah_name")
    suspend fun findSurah(
        @Path("surah_name") surah_name: String
    ): SearchResponse
}