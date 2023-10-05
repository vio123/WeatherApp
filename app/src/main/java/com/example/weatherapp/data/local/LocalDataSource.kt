package com.example.weatherapp.data.local

import com.example.weatherapp.data.local.dao.TemperatureDao
import com.example.weatherapp.data.local.entity.WeatherEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val temperatureDao: TemperatureDao,
) {
    fun getData(): WeatherEntity {
        return temperatureDao.getAllTemperatures()[0]
    }

    suspend fun deleteAll() {
        temperatureDao.deleteAll()
    }

    suspend fun insertData(data: WeatherEntity) {
        temperatureDao.insert(data)
    }
}