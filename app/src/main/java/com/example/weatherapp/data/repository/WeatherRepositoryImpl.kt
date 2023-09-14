package com.example.weatherapp.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.weatherapp.common.DataState
import com.example.weatherapp.data.local.LocalDataSource
import com.example.weatherapp.data.remote.RemoteDataSource
import com.example.weatherapp.data.utils.MyEntityToDomainMapper
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.repository.WeatherRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val sharedPreferences: SharedPreferences,
    private val mapper:MyEntityToDomainMapper
) :
    WeatherRepository {


    override suspend fun getWeather(latitude: Double, longitude: Double): Weather {
        val lastApiCallTime = sharedPreferences.getLong(KEY_LAST_API_CALL_TIME, 0)
        val currentTime = System.currentTimeMillis()
        val differenceInMinutes = TimeUnit.MILLISECONDS.toMinutes(currentTime - lastApiCallTime)
        return if (differenceInMinutes >= 1) {
            // Se face un apel la API pentru datele actuale
            val weather = remoteDataSource.getWeatherRemote(latitude, longitude)
            if(weather is DataState.Success) {
                localDataSource.deleteAll()
                saveWeatherToLocalDatabase(weather.data)
                updateLastApiCallTime(currentTime)
                weather.data
            }
            mapper.mapToDomain(localDataSource.getWeatherLocal())
        } else {
            mapper.mapToDomain(localDataSource.getWeatherLocal())
        }
    }
    private suspend fun saveWeatherToLocalDatabase(weather: Weather) {
        // Salvează datele în baza de date locală
        localDataSource.insertWeather(mapper.mapToSource(weather))
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