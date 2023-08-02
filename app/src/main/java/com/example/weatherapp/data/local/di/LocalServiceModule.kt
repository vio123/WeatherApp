package com.example.weatherapp.data.local.di

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.data.local.dao.TemperatureDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WeatherRoom

@Module
@InstallIn(SingletonComponent::class)
object LocalServiceModule {
    @Provides
    @Singleton
    @WeatherRoom
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "weather_database"
        ).build()
    }
    @Provides
    @Singleton
    fun provideWeatherDao(@WeatherRoom database: AppDatabase): TemperatureDao {
        return database.temperatureDao()
    }
}