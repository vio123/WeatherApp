package com.example.weatherapp.data.utils

import com.example.weatherapp.common.DataState
import retrofit2.Response

class DefaultDtoInterpreter :DtoInterpreter {
    override fun <DTO, DOMAIN_MODEL> interpret(
        response: Response<DTO>,
        mapper: DomainMapper<DTO, DOMAIN_MODEL>
    ): DataState<DOMAIN_MODEL> {
        if(response.isSuccessful){
            val body = response.body()
            if(body != null){
                return try {
                    DataState.Success(mapper.mapToDomain(body))
                }catch (e:Exception){
                    DataState.Error(e.fillInStackTrace(),null)
                }
            }
        }
        return DataState.OtherError("Nu stiu ce nu a mers")
    }
}