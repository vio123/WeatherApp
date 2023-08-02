package com.example.weatherapp.data.remote.dto

import com.squareup.moshi.Json

data class TemperatureDto(
    @Json(name = "temperature")
    val temperature:Double
)
