package com.example.weatherapp.data.utils

import com.example.weatherapp.data.remote.dto.TemperatureDto
import com.example.weatherapp.data.remote.dto.WeatherDto
import com.example.weatherapp.domain.model.Weather

class MyDtoToDomainMapper : DomainMapper<WeatherDto, Weather> {
    override fun mapToDomain(entity: WeatherDto): Weather {
        return Weather(
            temperature = entity.currentTemperatureDto.temperature
        )
    }

    override fun mapToSource(domainModel: Weather): WeatherDto {
        return WeatherDto(
            currentTemperatureDto = TemperatureDto(
                temperature = domainModel.temperature
            )
        )
    }
}