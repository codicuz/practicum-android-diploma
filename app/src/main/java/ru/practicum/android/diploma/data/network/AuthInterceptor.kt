package ru.practicum.android.diploma.data.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.practicum.android.diploma.BuildConfig

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer ${BuildConfig.API_ACCESS_TOKEN}")
            .method(originalRequest.method, originalRequest.body)
            .build()

        return chain.proceed(newRequest)
    }
}
