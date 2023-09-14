package com.example.weatherapp.data.remote

import android.util.Log
import com.example.weatherapp.common.DataState
import com.example.weatherapp.data.remote.api.WeatherApiService
import com.example.weatherapp.data.remote.dto.WeatherDto
import com.example.weatherapp.data.utils.DefaultDtoInterpreter
import com.example.weatherapp.data.utils.MyDtoToDomainMapper
import com.example.weatherapp.domain.model.Weather
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val weatherApiService: WeatherApiService,
    private val defaultDtoInterpreter: DefaultDtoInterpreter
){
     suspend fun getWeatherRemote(latitude: Double, longitude: Double): DataState<Weather> {
         val weatherResponse = weatherApiService.getWeather(latitude, longitude)
         return defaultDtoInterpreter.interpret(weatherResponse, MyDtoToDomainMapper())
    }
}