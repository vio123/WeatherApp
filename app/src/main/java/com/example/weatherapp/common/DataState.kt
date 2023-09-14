package com.example.weatherapp.common


sealed class DataState<out R> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error<out T>(val exception: Throwable,val data:T) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
    data class OtherError(val error: String) : DataState<Nothing>()
}
