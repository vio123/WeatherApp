package com.example.weatherapp.data.utils

interface DomainMapper<DTO,DOMAIN_MODEL> {
    fun mapToDomain(entity: DTO): DOMAIN_MODEL

    fun mapToSource(domainModel: DOMAIN_MODEL): DTO
}