package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.Response

const val ResultCode200 = 200
const val ResultCode500 = 500

object ResponseFactory {
    suspend inline fun <reified T : Any> create(
        crossinline apiCall: suspend () -> T,
        crossinline responseCreator: (T) -> Response

    ): Response {
        return try {
            val result = apiCall()
            responseCreator(result).apply { resultCode = ResultCode200 }
        } catch (_: Exception) {
            Response().apply { resultCode = ResultCode500 }
        }
    }
}
