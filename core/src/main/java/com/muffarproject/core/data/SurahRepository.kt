package com.muffarproject.core.data

import com.muffarproject.core.data.source.local.LocalDataSource
import com.muffarproject.core.data.source.remote.RemoteDataSource
import com.muffarproject.core.data.source.remote.network.ApiResponse
import com.muffarproject.core.data.source.remote.response.SurahResponse
import com.muffarproject.core.domain.model.Surah
import com.muffarproject.core.domain.repository.ISurahRepository
import com.muffarproject.core.utils.AppExecutors
import com.muffarproject.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SurahRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : ISurahRepository {
    override fun getAllSurah(): Flow<Resource<List<Surah>>> =
        object : NetworkBoundResource<List<Surah>, List<SurahResponse>>() {
            override fun loadFromDB(): Flow<List<Surah>> {
                return localDataSource.getAllSurah().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Surah>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<SurahResponse>>> =
                remoteDataSource.getListSurah()

            override suspend fun saveCallResult(data: List<SurahResponse>) {
                val surahList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertSurah(surahList)
            }
        }.asFlow()

    override fun getFavoriteSurah(): Flow<List<Surah>> {
        return localDataSource.getFavoriteSurah().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteSurah(surah: Surah, state: Boolean) {
        val surahEntity = DataMapper.mapDomainToEntity(surah)
        appExecutors.diskIO().execute { localDataSource.setFavoriteSurah(surahEntity, state) }
    }
}