package ru.practicum.android.diploma.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.data.dto.VacancySearchRequest
import ru.practicum.android.diploma.data.dto.VacancySearchResponse
import ru.practicum.android.diploma.data.network.NO_INTERNET_CODE
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.ResultCode200
import ru.practicum.android.diploma.domain.api.VacancyRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancySearchResult
import ru.practicum.android.diploma.util.Resource

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient
) : VacancyRepository {

    override fun searchVacancies(
        query: String,
        page: Int,
        perPage: Int
    ): Flow<Resource<VacancySearchResult>> = flow {
        val response = networkClient.doRequest(
            VacancySearchRequest(
                text = query,
                page = page,
                perPage = perPage
            )
        )

        when (response.resultCode) {
            NO_INTERNET_CODE -> {
                emit(Resource.Error("No internet connection", NO_INTERNET_CODE))
            }

            ResultCode200 -> {
                val searchResponse = response as VacancySearchResponse
                val vacancies = searchResponse.items.map { dto ->
                    Vacancy(
                        id = dto.id,
                        name = dto.name,
                        employerName = dto.employer?.name ?: "",
                        employerLogoUrl = dto.employer?.logoUrls?.logo90,
                        areaName = dto.area?.name ?: "",
                        salaryFrom = dto.salary?.from,
                        salaryTo = dto.salary?.to,
                        salaryCurrency = dto.salary?.currency
                    )
                }
                emit(
                    Resource.Success(
                        VacancySearchResult(
                            vacancies = vacancies,
                            found = searchResponse.found,
                            page = searchResponse.page,
                            pages = searchResponse.pages
                        )
                    )
                )
            }

            else -> {
                emit(Resource.Error("Server error", response.resultCode))
            }
        }
    }.flowOn(Dispatchers.IO)
}
