package com.example.weatherapp.data.utils

import com.example.weatherapp.data.local.entity.WeatherEntity
import com.example.weatherapp.domain.model.Weather

class MyEntityToDomainMapper:DomainMapper<WeatherEntity,Weather> {
    override fun mapToDomain(entity: WeatherEntity): Weather {
        return Weather(
            temperature = entity.temperature
        )
    }

    override fun mapToSource(domainModel: Weather): WeatherEntity {
       return WeatherEntity(
           temperature = domainModel.temperature
       )
    }
}