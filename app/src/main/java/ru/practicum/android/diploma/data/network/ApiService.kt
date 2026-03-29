package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.Areas
import ru.practicum.android.diploma.data.dto.Industries
import ru.practicum.android.diploma.data.dto.VacancySearchResponse

interface ApiService {
    @GET("areas")
    suspend fun getAreas(): List<Areas>

    @GET("industries")
    suspend fun getIndustries(): List<Industries>

    @GET("vacancies")
    suspend fun searchVacancies(@QueryMap options: Map<String, String>): VacancySearchResponse

}
