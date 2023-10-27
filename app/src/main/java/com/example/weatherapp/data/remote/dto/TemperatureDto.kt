package com.example.weatherapp.data.remote.dto

import com.squareup.moshi.Json

data class TemperatureDto(
    @Json(name = "temperature")
    var temperature:Double = -5000.0
)
