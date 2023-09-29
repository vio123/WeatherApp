package com.example.weatherapp.data.common

import com.example.weatherapp.common.DataState
import com.example.weatherapp.data.utils.DomainMapper


class OfflineBoundResource {
    companion object {
        suspend fun <Input, Output> fetch(
            differenceTime: Long,
            setDiff: Long,
            getRemote: suspend () -> DataState<Output>,
            mapper: DomainMapper<Input, Output>,
            localDataSource: LocalDataSource<Input>,
            updateLastApiCalls: () -> Unit
        ): Output {
            if (differenceTime >= setDiff) {
                val remote = getRemote()
                if (remote is DataState.Success) {
                    localDataSource.deleteAll()
                    localDataSource.insertData(mapper.mapToSource(remote.data))
                    updateLastApiCalls()
                }
            }
            return mapper.mapToDomain(localDataSource.getData()!!)
        }
    }
}