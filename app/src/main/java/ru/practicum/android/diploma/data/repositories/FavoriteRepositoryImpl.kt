package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.db.VacanciesDao
import ru.practicum.android.diploma.data.db.VacancyDbConverter
import ru.practicum.android.diploma.data.db.VacancyEntity
import ru.practicum.android.diploma.domain.api.FavoriteRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetail

class FavoriteRepositoryImpl(
    private val dao: VacanciesDao,
    private val vacancyDbConverter: VacancyDbConverter
): FavoriteRepository {

    suspend override fun addVacancy(vacancy: VacancyDetail) {
        dao.insert(vacancyDbConverter.map(vacancy))
    }

    suspend override fun deleteVacancy(idVacancy: String) {
        dao.delete(idVacancy)
    }

    suspend override fun loadVacancies(): Flow<List<Vacancy>> = flow {
        val vacancies = convert(dao.getAll())
        emit(vacancies)
    }

    private fun convert(vacancies: List<VacancyEntity>): List<Vacancy> {
        return vacancies.map {vacancy ->
            VacancyDetailToVacancy(vacancyDbConverter.map(vacancy))
        }
    }

    private fun VacancyDetailToVacancy(vacancy: VacancyDetail): Vacancy{
        return Vacancy(
            vacancy.id,
            vacancy.name,
            vacancy.employerName,
            vacancy.employerLogo,
            vacancy.areaName,
            vacancy.salaryFrom,
            vacancy.salaryTo,
            vacancy.salaryCurrency,
        )
    }
}

