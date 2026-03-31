package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.VacancyInteractor
import ru.practicum.android.diploma.domain.api.VacancyRepository
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.domain.models.VacancySearchResult
import ru.practicum.android.diploma.util.Resource

class VacancyInteractorImpl(
    private val repository: VacancyRepository
) : VacancyInteractor {
    override fun searchVacancies(
        query: String,
        page: Int,
        perPage: Int,
        salary: Int?,
        onlyWithSalary: Boolean,
        industry: Int?,
        area: Int?
    ): Flow<Resource<VacancySearchResult>> {
        return repository.searchVacancies(query, page, perPage, salary, onlyWithSalary, industry, area)
    }

    override fun getVacancyById(id: String): Flow<Resource<VacancyDetail>> {
        return repository.getVacancyById(id)
    }
}
