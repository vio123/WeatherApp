package com.example.weatherapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.DataState
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
) : ViewModel() {
    private val _weatherViewState = MutableLiveData<DataState<Weather>>()
    val weatherViewState: LiveData<DataState<Weather>>
        get() = _weatherViewState
    fun getWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _weatherViewState.value = DataState.Loading
            try {
                val result = withContext(Dispatchers.IO) {
                   getWeatherUseCase(latitude, longitude)
                }
                _weatherViewState.value = DataState.Success(result)
            } catch (e: Exception) {
                Log.e("test123",e.message.toString())
                _weatherViewState.value = DataState.Error(e)
            }
        }
    }
}
