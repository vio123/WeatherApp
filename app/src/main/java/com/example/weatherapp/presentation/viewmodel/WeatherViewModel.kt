package com.example.weatherapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.usecase.GetWeatherUseCase
import com.example.weatherapp.presentation.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val useCase: GetWeatherUseCase
) : ViewModel() {
    private val _weatherViewState = MutableLiveData<DataState>()
    val weatherViewState: LiveData<DataState>
        get() = _weatherViewState
    fun getWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _weatherViewState.value = DataState.Loading
            try {
                val result = withContext(Dispatchers.IO) {
                    useCase(latitude, longitude)
                }
                _weatherViewState.value = DataState.Success(result)
            } catch (e: Exception) {
                _weatherViewState.value = DataState.Error("Eroare la preluarea datelor meteorologice: ${e.message}")
            }
        }
    }
}
