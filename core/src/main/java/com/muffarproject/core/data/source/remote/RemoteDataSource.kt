package com.muffarproject.core.data.source.remote

import android.util.Log
import com.muffarproject.core.data.source.remote.network.ApiResponse
import com.muffarproject.core.data.source.remote.network.ApiService
import com.muffarproject.core.data.source.remote.response.SurahResponse
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
                Log.d(TAG, "getListSurah: $surahs")
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

    companion object {
        const val TAG = "RemoteDataSource"
    }
}