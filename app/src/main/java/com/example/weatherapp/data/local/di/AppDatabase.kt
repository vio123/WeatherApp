package com.example.weatherapp.data.local.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.data.local.dao.TemperatureDao
import com.example.weatherapp.data.local.entity.WeatherEntity

@Database(entities = [WeatherEntity::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun temperatureDao(): TemperatureDao
}