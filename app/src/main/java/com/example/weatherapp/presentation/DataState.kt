package com.example.weatherapp.presentation

import com.example.weatherapp.domain.model.Weather

sealed class DataState {
    object Loading : DataState()
    data class Success(val weather: Weather) : DataState()
    data class Error(val message: String) : DataState()
}

