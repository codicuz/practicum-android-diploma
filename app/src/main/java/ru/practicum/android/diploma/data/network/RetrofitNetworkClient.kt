package ru.practicum.android.diploma.data.network

import android.util.Log
import ru.practicum.android.diploma.data.dto.AreasRequest
import ru.practicum.android.diploma.data.dto.AreasResponse
import ru.practicum.android.diploma.data.dto.IndustriesRequest
import ru.practicum.android.diploma.data.dto.IndustriesResponse
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacancyDetailRequest
import ru.practicum.android.diploma.data.dto.VacancyDetailResponse
import ru.practicum.android.diploma.data.dto.VacancySearchRequest
import ru.practicum.android.diploma.util.Constants.HTTP_BAD_REQUEST

class RetrofitNetworkClient(private val apiService: ApiService) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        val response = when (dto) {
            is AreasRequest -> ResponseFactory.create(
                apiCall = { apiService.getAreas() },
                responseCreator = { AreasResponse(it) }
            )

            is IndustriesRequest -> ResponseFactory.create(
                apiCall = { apiService.getIndustries() },
                responseCreator = { IndustriesResponse(it) }
            )

            is VacancySearchRequest -> {
                val options = buildSearchOptions(dto)
                ResponseFactory.create(
                    apiCall = { apiService.searchVacancies(options) },
                    responseCreator = { it }
                )
            }

            is VacancyDetailRequest -> ResponseFactory.create(
                apiCall = { apiService.getVacancyById(dto.id) },
                responseCreator = { VacancyDetailResponse(it) }
            )

            else -> Response().apply { resultCode = HTTP_BAD_REQUEST }
        }
        return response
    }

    private fun buildSearchOptions(request: VacancySearchRequest): Map<String, String> {
        val options = mutableMapOf<String, String>()
        options["text"] = request.text
        options["page"] = request.page.toString()
        options["per_page"] = request.perPage.toString()

        request.salary?.let { options["salary"] = it.toString() }
        if (request.onlyWithSalary) options["only_with_salary"] = "true"
        request.industry?.let { options["industry"] = it.toString() }
        request.area?.let { options["area"] = it.toString() }

        Log.d("API_REQ", "Options: $options")

        return options
    }
}
