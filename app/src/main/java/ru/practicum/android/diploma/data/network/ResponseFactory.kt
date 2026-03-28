package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.util.Constants.HTTP_INTERNAL_SERVER_ERROR
import ru.practicum.android.diploma.util.Constants.HTTP_OK

object ResponseFactory {
    suspend inline fun <reified T : Any> create(
        crossinline apiCall: suspend () -> T,
        crossinline responseCreator: (T) -> Response
    ): Response {
        return try {
            val result = apiCall()
            responseCreator(result).apply { resultCode = HTTP_OK }
        } catch (e: Exception) {
            Response().apply { resultCode = HTTP_INTERNAL_SERVER_ERROR }
        }
    }
}
