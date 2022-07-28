package com.muffarproject.core.data.source.remote

import android.util.Log
import com.muffarproject.core.data.source.remote.network.ApiResponse
import com.muffarproject.core.data.source.remote.network.ApiService
import com.muffarproject.core.data.source.remote.response.ListSurahResponse
import com.muffarproject.core.data.source.remote.response.SurahResponse
import com.muffarproject.core.data.source.remote.response.VerseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getListSurah(): Flow<ApiResponse<List<SurahResponse>>> {
        return flow {
            try {
                val response = apiService.getListSurah()
                val surahs = response.surahs
                if (surahs.isNotEmpty()) {
                    emit(ApiResponse.Success(surahs))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Log.e(TAG, "getListSurah: ${e.message.toString()}")
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailSurah(surahNumber: String): Flow<ApiResponse<List<VerseResponse>>> {
        return flow {
            try {
                val response = apiService.getDetailSurah(surahNumber)
                val verses = response.verses
                if (verses.isNotEmpty()) {
                    emit(ApiResponse.Success(verses))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Log.e(TAG, "getDetailSurah: ${e.message.toString()}")
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getSurahByName(query: String): Flow<ApiResponse<List<SurahResponse>>> {
        return flow {
            try {
                val response = apiService.findSurah(query)
                val surahs = response.surahs
                if (surahs.isNotEmpty()) {
                    emit(ApiResponse.Success(surahs))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Log.d(TAG, "getSurahByName: ${e.message.toString()}")
            }
        }.flowOn(Dispatchers.IO)
    }

    companion object {
        const val TAG = "RemoteDataSource"
    }
}