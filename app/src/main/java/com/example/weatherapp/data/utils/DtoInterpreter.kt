package com.example.weatherapp.data.utils

import com.example.weatherapp.common.DataState
import retrofit2.Response

interface DtoInterpreter {
    fun <DTO, DOMAIN_MODEL> interpret(
        response: Response<DTO>,
        mapper: DomainMapper<DTO, DOMAIN_MODEL>,
    ): DataState<DOMAIN_MODEL>
}