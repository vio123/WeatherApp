package com.example.weatherapp.data.repository

import com.example.weatherapp.common.DataState
import com.example.weatherapp.data.local.dao.TemperatureDao
import com.example.weatherapp.data.local.entity.WeatherEntity
import com.example.weatherapp.data.remote.RemoteDataSource
import com.example.weatherapp.data.remote.dto.WeatherDto
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val temperatureDao: TemperatureDao,
    private val remoteDataSource: RemoteDataSource
) :
    WeatherRepository {

    override suspend fun getWeather(latitude: Double, longitude: Double): Weather {

        return when (val weatherDataState = remoteDataSource.getWeatherRemote(latitude, longitude)) {
            is DataState.Loading -> {
                // În cazul în care este încărcare, poți arunca o excepție sau să gestionezi altfel această stare
                throw IllegalStateException("Eroare: Încărcare în curs")
            }
            is DataState.Success -> {
                val weather = weatherDataState.data.toWeather()
                temperatureDao.insert(weatherDataState.data.toWeatherEntity())
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