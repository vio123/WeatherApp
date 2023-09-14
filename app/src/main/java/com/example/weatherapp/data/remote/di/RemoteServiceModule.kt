package com.example.weatherapp.data.remote.di

import com.example.weatherapp.data.remote.api.WeatherApiService
import com.example.weatherapp.data.utils.DefaultDtoInterpreter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RemoteServiceModule {
    @Provides
    fun provideWeatherApiService(@WeatherRetrofitClient retrofit:Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDefaultDtoInterpreter(): DefaultDtoInterpreter {
        return DefaultDtoInterpreter() // Instantiați aici un obiect DefaultDtoInterpreter fără parametri, dacă este posibil
    }
}
