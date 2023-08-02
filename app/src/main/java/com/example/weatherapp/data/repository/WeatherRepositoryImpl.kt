package com.example.weatherapp.data.repository

import android.util.Log
import com.example.weatherapp.data.local.dao.TemperatureDao
import com.example.weatherapp.data.local.entity.WeatherEntity
import com.example.weatherapp.data.remote.api.DataState
import com.example.weatherapp.data.remote.api.WeatherApiService
import com.example.weatherapp.data.remote.dto.WeatherDto
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApiService: WeatherApiService,
    private val temperatureDao: TemperatureDao
) :
    WeatherRepository {
    private suspend fun getWeatherApi(latitude: Double, longitude: Double): DataState<Weather> {
        return try {
            DataState.Loading
            val weatherResponse = weatherApiService.getWeather(latitude, longitude)
            val weather = weatherResponse.toWeather()
            temperatureDao.insert(weatherResponse.toWeatherEntity())
            DataState.Success(weather)
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }

    override suspend fun getWeatherRemote(latitude: Double, longitude: Double): Weather {

        return when (val weatherDataState = getWeatherApi(latitude, longitude)) {
            is DataState.Loading -> {
                // În cazul în care este încărcare, poți arunca o excepție sau să gestionezi altfel această stare
                throw IllegalStateException("Eroare: Încărcare în curs")
            }
            is DataState.Success -> {
                val weather = weatherDataState.data
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

    private fun WeatherDto.toWeather(): Weather {
        return Weather(
            temperature = this.currentTemperatureDto.temperature
        )
    }

    private fun WeatherDto.toWeatherEntity(): WeatherEntity {
        return WeatherEntity(
            temperature = this.currentTemperatureDto.temperature
        )
    }
}