package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.AreasRequest
import ru.practicum.android.diploma.data.dto.AreasResponse
import ru.practicum.android.diploma.data.dto.IndustriesRequest
import ru.practicum.android.diploma.data.dto.IndustriesResponse
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacancySearchRequest
import ru.practicum.android.diploma.util.NetworkConnectivityChecker

const val ResultCode400 = 400
const val NO_INTERNET_CODE = -1

class RetrofitNetworkClient(
    private val apiService: ApiService,
    private val connectivityChecker: NetworkConnectivityChecker
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        if (!connectivityChecker.isConnected()) {
            return Response().apply { resultCode = NO_INTERNET_CODE }
        }

        return when (dto) {
            is VacancySearchRequest -> ResponseFactory.create(
                apiCall = {
                    apiService.searchVacancies(
                        mapOf(
                            "text" to dto.text,
                            "page" to dto.page.toString(),
                            "per_page" to dto.perPage.toString()
                        )
                    )
                },
                responseCreator = { it }
            )

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
