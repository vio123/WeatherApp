package com.example.weatherapp.data.repository

import android.content.SharedPreferences
import android.icu.util.Output
import androidx.core.content.edit
import com.example.weatherapp.common.DataState
import com.example.weatherapp.data.common.OfflineBoundResource
import com.example.weatherapp.data.local.LocalDataSource
import com.example.weatherapp.data.local.entity.WeatherEntity
import com.example.weatherapp.data.remote.RemoteDataSource
import com.example.weatherapp.data.remote.dto.WeatherDto
import com.example.weatherapp.data.utils.MyEntityToDomainMapper
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.repository.WeatherRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val sharedPreferences: SharedPreferences,
    private val mapper: MyEntityToDomainMapper,
) :
    WeatherRepository {
    override suspend fun getWeather(latitude: Double, longitude: Double): Weather {
        val lastApiCallTime = sharedPreferences.getLong(KEY_LAST_API_CALL_TIME, 0)
        val currentTime = System.currentTimeMillis()
        val differenceInMinutes = TimeUnit.MILLISECONDS.toMinutes(currentTime - lastApiCallTime)
        return OfflineBoundResource.fetch(
            conditie = {
                differenceInMinutes >= 1
            },
            getRemote = {
                remoteDataSource.getWeatherRemote(latitude, longitude)
            },
            mapper = mapper,
            insert = { dtos ->
                localDataSource.insertData(dtos)
            },
            deleteAll = {
                localDataSource.deleteAll()
            },
            getData = {
                localDataSource.getData()
            },
            updateLastApiCalls = {
                updateLastApiCallTime(currentTime)
            }
        )
    }

    private fun updateLastApiCallTime(currentTime: Long) {
        // Actualizează ultima dată a apelului la API în Shared Preferences
        sharedPreferences.edit {
            putLong(KEY_LAST_API_CALL_TIME, currentTime)
        }
    }

    companion object {
        const val KEY_LAST_API_CALL_TIME = "LAST_API_CALL_TIME"
    }
}