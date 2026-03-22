package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.AreasRequest
import ru.practicum.android.diploma.data.dto.AreasResponse
import ru.practicum.android.diploma.data.dto.IndustriesRequest
import ru.practicum.android.diploma.data.dto.IndustriesResponse
import ru.practicum.android.diploma.data.dto.Response

const val ResultCode400 = 400

class RetrofitNetworkClient(private val apiService: ApiService) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        return when (dto) {
            is AreasRequest -> ResponseFactory.create(
                apiCall = { apiService.getAreas() },
                responseCreator = { AreasResponse(it) }
            )

            is IndustriesRequest -> ResponseFactory.create(
                apiCall = { apiService.getIndustries() },
                responseCreator = { IndustriesResponse(it) }
            )

            else -> Response().apply { resultCode = ResultCode400 }
        }
    }
}
