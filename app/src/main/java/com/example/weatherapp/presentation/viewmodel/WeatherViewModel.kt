package com.example.weatherapp.presentation.viewmodel

import android.os.Handler
import android.os.Looper
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
    private val updateHandler = Handler(Looper.getMainLooper())
    private val updateIntervalMillis = 60000L // Intervalul de actualizare: 10 minut

    //exception handler coroutine
    fun getWeather(latitude: Double, longitude: Double) {
        updateHandler.post(object : Runnable {
            override fun run() {
                viewModelScope.launch {
                    _weatherViewState.value = DataState.Loading
                    try {
                        val result = withContext(Dispatchers.IO) {
                            getWeatherUseCase(latitude, longitude)
                        }
                        _weatherViewState.value = DataState.Success(result)
                    } catch (e: Exception) {
                        Log.e("test123", e.message.toString())
                        _weatherViewState.value = DataState.Error(e, Weather())
                    }
                }
                updateHandler.postDelayed(this, updateIntervalMillis)
            }

        })
    }

    fun stopWeatherUpdates() {
        updateHandler.removeCallbacksAndMessages(null)
    }
}
