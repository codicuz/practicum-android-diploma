package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.domain.models.VacancySearchResult
import ru.practicum.android.diploma.util.Resource

interface VacancyInteractor {
    fun searchVacancies(
        query: String,
        page: Int,
        perPage: Int
    ): Flow<Resource<VacancySearchResult>>

    fun getVacancyById(id: String): Flow<Resource<VacancyDetail>>
}
