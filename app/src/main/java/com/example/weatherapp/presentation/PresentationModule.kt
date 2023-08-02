package com.example.weatherapp.presentation

import com.example.weatherapp.domain.usecase.GetWeatherUseCase
import com.example.weatherapp.presentation.viewmodel.WeatherViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object PresentationModule {

}
