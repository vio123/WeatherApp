package com.example.weatherapp.data.local

import com.example.weatherapp.data.common.LocalDataSource
import com.example.weatherapp.data.local.dao.TemperatureDao
import com.example.weatherapp.data.local.entity.WeatherEntity
import javax.inject.Inject

class MyLocalDataSource @Inject constructor(
    private val temperatureDao: TemperatureDao,
) : LocalDataSource<WeatherEntity> {
    override suspend fun getData(): WeatherEntity {
        return temperatureDao.getAllTemperatures()[0]
    }

    override suspend fun deleteAll() {
        temperatureDao.deleteAll()
    }

    override suspend fun insertData(data: WeatherEntity) {
        temperatureDao.insert(data)
    }
}