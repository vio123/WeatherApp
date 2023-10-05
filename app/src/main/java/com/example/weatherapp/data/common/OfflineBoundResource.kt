package com.example.weatherapp.data.common

import android.util.Log
import com.example.weatherapp.common.DataState
import com.example.weatherapp.data.utils.DomainMapper
import com.example.weatherapp.domain.model.Weather


class OfflineBoundResource {
    companion object {
        suspend fun <Input, Output> fetch(
            conditie: () -> Boolean,
            getRemote: suspend () -> DataState<Output>,
            mapper: DomainMapper<Input, Output>,
            insert: suspend (Input) -> Unit,
            deleteAll: suspend () -> Unit,
            getData: suspend () -> Input,
            updateLastApiCalls: () -> Unit
        ): Output {
            if (conditie()) {
                Log.e("test123","sa indeplinit conditia")
                val remote = getRemote.invoke()
                if (remote is DataState.Success) {
                    deleteAll.invoke()
                    insert.invoke(mapper.mapToSource(remote.data))
                    updateLastApiCalls()
                }
            }
            return mapper.mapToDomain(getData.invoke())
        }
    }
}