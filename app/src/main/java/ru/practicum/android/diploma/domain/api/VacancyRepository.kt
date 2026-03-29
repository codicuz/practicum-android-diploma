package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancySearchResult
import ru.practicum.android.diploma.util.Resource

interface VacancyRepository {
    fun searchVacancies(
        query: String,
        page: Int,
        perPage: Int
    ): Flow<Resource<VacancySearchResult>>
}
