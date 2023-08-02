package com.example.weatherapp.data.repository

import android.content.SharedPreferences
import android.provider.Settings.Global.putLong
import android.util.Log
import androidx.core.content.edit
import com.example.weatherapp.common.DataState
import com.example.weatherapp.data.local.LocalDataSource
import com.example.weatherapp.data.local.dao.TemperatureDao
import com.example.weatherapp.data.local.entity.WeatherEntity
import com.example.weatherapp.data.remote.RemoteDataSource
import com.example.weatherapp.data.remote.dto.WeatherDto
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.repository.WeatherRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val temperatureDao: TemperatureDao,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val sharedPreferences: SharedPreferences
) :
    WeatherRepository {

    private suspend fun getWeatherRemote(latitude: Double, longitude: Double): Weather {
        return when (val weatherDataState =
            remoteDataSource.getWeatherRemote(latitude, longitude)) {
            is DataState.Loading -> {
                // În cazul în care este încărcare, poți arunca o excepție sau să gestionezi altfel această stare
                throw IllegalStateException("Eroare: Încărcare în curs")
            }
            is DataState.Success -> {
                val weather = weatherDataState.data.toWeather()
                weather // Returnezi obiectul Weather din starea de succes
            }
            is DataState.Error -> {
                // În cazul în care este eroare, poți arunca o excepție sau să gestionezi altfel această stare
                throw IllegalStateException("Eroare la preluarea datelor meteorologice: ${weatherDataState}")
            }
            else -> {
                throw IllegalStateException("Eroare la preluarea datelor meteorologice: ${weatherDataState}")
            }
        }
    }

    override suspend fun getWeather(latitude: Double, longitude: Double): Weather {
        val lastApiCallTime = sharedPreferences.getLong(KEY_LAST_API_CALL_TIME, 0)
        val currentTime = System.currentTimeMillis()
        val differenceInMinutes = TimeUnit.MILLISECONDS.toMinutes(currentTime - lastApiCallTime)
        return if (differenceInMinutes >= 1) {
            // Se face un apel la API pentru datele actuale
            val weather = getWeatherRemote(latitude, longitude)
            saveWeatherToLocalDatabase(weather)
            updateLastApiCallTime(currentTime)
            weather
        } else {
            // Se preiau datele din baza de date locală
            localDataSource.getWeatherLocal().toWeather()
        }
    }
    private suspend fun saveWeatherToLocalDatabase(weather: Weather) {
        // Salvează datele în baza de date locală
        temperatureDao.insert(weather.toWeatherEntity())
    }

    private fun updateLastApiCallTime(currentTime: Long) {
        // Actualizează ultima dată a apelului la API în Shared Preferences
        sharedPreferences.edit {
            putLong(KEY_LAST_API_CALL_TIME, currentTime)
        }
    }

    private fun WeatherDto.toWeather(): Weather {
        return Weather(
            temperature = this.currentTemperatureDto.temperature
        )
    }

    private fun WeatherEntity.toWeather(): Weather{
        return Weather(
            temperature = temperature
        )
    }

    private fun WeatherDto.toWeatherEntity(): WeatherEntity {
        return WeatherEntity(
            temperature = this.currentTemperatureDto.temperature
        )
    }
    private fun Weather.toWeatherEntity():WeatherEntity{
        return WeatherEntity(
            temperature = temperature
        )
    }
    companion object {
        const val KEY_LAST_API_CALL_TIME = "LAST_API_CALL_TIME"
    }
}