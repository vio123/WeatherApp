package com.example.weatherapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val useCase: GetWeatherUseCase
) : ViewModel() {
    val weatherData = MutableLiveData<Weather>()
    val error = MutableLiveData<String>()

    fun getWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    useCase(latitude, longitude)
                }
                weatherData.value = result
            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }
}
