package com.example.weatherapp.data.common

interface LocalDataSource<T> {
    suspend fun getData(): T?
    suspend fun insertData(data: T)
    suspend fun deleteAll()
}