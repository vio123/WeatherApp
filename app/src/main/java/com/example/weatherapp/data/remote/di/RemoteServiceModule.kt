package com.example.weatherapp.data.remote.di

import com.example.weatherapp.data.remote.api.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
object RemoteServiceModule {
    @Provides
    fun provideWeatherApiService(@WeatherRetrofitClient retrofit:Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }
}
