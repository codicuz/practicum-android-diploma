package ru.practicum.android.diploma.util

sealed interface Resource<T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Error<T>(val message: String, val code: Int? = null) : Resource<T>
}
