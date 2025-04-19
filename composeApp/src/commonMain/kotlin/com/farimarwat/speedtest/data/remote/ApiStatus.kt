package com.farimarwat.speedtest.data.remote

sealed class ApiStatus<out T> {
    data class Success<out T>(val data: T) : ApiStatus<T>()
    object Loading : ApiStatus<Nothing>()
    object Empty : ApiStatus<Nothing>()
    data class Error(val message: String) : ApiStatus<Nothing>()
}