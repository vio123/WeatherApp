package com.example.weatherapp.data.repository

import android.util.Log
import com.example.weatherapp.data.local.dao.TemperatureDao
import com.example.weatherapp.data.local.entity.WeatherEntity
import com.example.weatherapp.data.remote.api.DataState
import com.example.weatherapp.data.remote.api.WeatherApiService
import com.example.weatherapp.data.remote.dto.WeatherDto
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.repository.WeatherRepository
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApiService: WeatherApiService,
    private val temperatureDao: TemperatureDao
) :
    WeatherRepository {

    override suspend fun getWeatherRemote(latitude: Double, longitude: Double): Weather {
        val weatherResponse = weatherApiService.getWeather(latitude, longitude)
        val weather = weatherResponse.toWeather()
        temperatureDao.insert(weatherResponse.toWeatherEntity())
        return weather
    }

    private fun WeatherDto.toWeather(): Weather {
        return Weather(
            temperature = this.currentTemperatureDto.temperature
        )
    }

    private fun WeatherDto.toWeatherEntity(): WeatherEntity{
        return WeatherEntity(
            temperature = this.currentTemperatureDto.temperature
        )
    }
}