package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject


class GetWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(latitude: Double, longitude: Double): Weather {
        return weatherRepository.getWeather(latitude, longitude)
    }
}