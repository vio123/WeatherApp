package com.example.weatherapp.data.remote.dto

import com.squareup.moshi.Json

data class WeatherDto(
    @Json(name = "current_weather")
    val currentTemperatureDto: TemperatureDto = TemperatureDto()
)
