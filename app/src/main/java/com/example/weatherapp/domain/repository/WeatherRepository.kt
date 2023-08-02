package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeatherRemote(latitude: Double, longitude: Double): Weather
}