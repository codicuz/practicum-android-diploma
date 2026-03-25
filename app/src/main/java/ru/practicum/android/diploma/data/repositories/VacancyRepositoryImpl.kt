package ru.practicum.android.diploma.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacancyDto
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
             emit(response(response))

    }.flowOn(Dispatchers.IO)


    private fun response(response: Response): Resource<VacancySearchResult> {
        return when (response.resultCode) {
            NO_INTERNET_CODE -> Resource.Error("Нет интернета", NO_INTERNET_CODE)
            ResultCode200 -> successResponse(response as VacancySearchResponse)
            else -> Resource.Error("Ошибка сервера", response.resultCode)
        }
    }
        private fun successResponse(response: VacancySearchResponse): Resource<VacancySearchResult> {
            return Resource.Success(
                VacancySearchResult(
                    vacancies = response.items.map { it.toDomain() },
                    found = response.found,
                    page = response.page,
                    pages = response.pages
                )
            )
        }

        private fun VacancyDto.toDomain(): Vacancy {
            return Vacancy(
                id = id,
                name = name,
                employerName = employer?.name ?: "",
                employerLogoUrl = employer?.logoUrls?.logo90,
                areaName = area?.name ?: "",
                salaryFrom = salary?.from,
                salaryTo = salary?.to,
                salaryCurrency = salary?.currency
            )
        }
    }
