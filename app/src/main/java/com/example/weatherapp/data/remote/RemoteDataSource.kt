package com.example.weatherapp.data.remote

import com.example.weatherapp.common.DataState
import com.example.weatherapp.data.remote.api.WeatherApiService
import com.example.weatherapp.data.remote.dto.WeatherDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val weatherApiService: WeatherApiService,
){
     suspend fun getWeatherRemote(latitude: Double, longitude: Double): DataState<WeatherDto> {
        return try {
            DataState.Loading
            val weatherResponse = weatherApiService.getWeather(latitude, longitude)
            DataState.Success(weatherResponse)
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }

}