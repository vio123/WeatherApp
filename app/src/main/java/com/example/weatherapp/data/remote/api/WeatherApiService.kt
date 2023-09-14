package com.example.weatherapp.data.remote.api

import com.example.weatherapp.data.remote.dto.WeatherDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("forecast")
    suspend fun getWeather(
        @Query("latitude")
        latitude: Double,
        @Query("longitude")
        longitude: Double,
        @Query("current_weather")
        currentWeather: Boolean = true
    ): Response<WeatherDto>
}