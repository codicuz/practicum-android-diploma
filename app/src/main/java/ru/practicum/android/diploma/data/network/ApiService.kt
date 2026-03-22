package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import ru.practicum.android.diploma.data.dto.Areas
import ru.practicum.android.diploma.data.dto.Industries

interface ApiService {
    @GET("areas")
    suspend fun getAreas(): List<Areas>

    @GET("industries")
    suspend fun getIndustries(): List<Industries>
}
