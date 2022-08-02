package com.muffarproject.core.data

import com.muffarproject.core.data.source.local.LocalDataSource
import com.muffarproject.core.data.source.remote.RemoteDataSource
import com.muffarproject.core.data.source.remote.network.ApiResponse
import com.muffarproject.core.data.source.remote.response.SurahResponse
import com.muffarproject.core.domain.model.Surah
import com.muffarproject.core.domain.model.Verse
import com.muffarproject.core.domain.repository.ISurahRepository
import com.muffarproject.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SurahRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ISurahRepository {
    override fun getAllSurah(): Flow<Resource<List<Surah>>> =
        object : NetworkBoundResource<List<Surah>, List<SurahResponse>>() {
            override fun loadFromDB(): Flow<List<Surah>> {
                return localDataSource.getAllSurah().map {
                    DataMapper.mapSurahEntitiesToSurah(it)
                }
            }

            override fun shouldFetch(data: List<Surah>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<SurahResponse>>> =
                remoteDataSource.getListSurah()

            override suspend fun saveCallResult(data: List<SurahResponse>) {
                val surahList = DataMapper.mapSurahResponsesToSurahEntities(data)
                localDataSource.insertSurah(surahList)
            }
        }.asFlow()

    override fun getDetailSurah(surahNumber: String): Flow<Resource<List<Verse>>> {
        return flow {
            emit(Resource.Loading())
            when (val apiResponse = remoteDataSource.getDetailSurah(surahNumber).first()) {
                is ApiResponse.Success -> {
                    val data = apiResponse.data.map {
                        DataMapper.mapVerseResponseToVerse(it)
                    }
                    emit(Resource.Success(data))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Success(listOf()))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.errorMessage))
                }
            }
        }
    }

    override fun getSurahByName(query: String): Flow<Resource<List<Surah>>> =
        object : NetworkBoundResource<List<Surah>, List<SurahResponse>>() {
            override fun loadFromDB(): Flow<List<Surah>> {
                return localDataSource.getSurahByName("%$query%").map {
                    DataMapper.mapSurahEntitiesToSurah(it)
                }
            }

            override fun shouldFetch(data: List<Surah>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<SurahResponse>>> =
                remoteDataSource.getSurahByName(query)

            override suspend fun saveCallResult(data: List<SurahResponse>) {
                val surahList = DataMapper.mapSurahResponsesToSurahEntities(data)
                localDataSource.insertSurah(surahList)
            }
        }.asFlow()

    override fun getFavoriteSurah(): Flow<List<Surah>> {
        return localDataSource.getFavoriteSurah().map {
            DataMapper.mapSurahEntitiesToSurah(it)
        }
    }

    override suspend fun setFavoriteSurah(surah: Surah, state: Boolean) {
        val surahEntity = DataMapper.mapSurahToSurahEntity(surah)
        localDataSource.setFavoriteSurah(surahEntity, state)
    }
}