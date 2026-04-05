package ru.practicum.android.diploma.data.repositories

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
) : FavoriteRepository {

    override suspend fun addVacancy(vacancy: VacancyDetail) {
        dao.insert(vacancyDbConverter.map(vacancy))
    }

    override suspend fun deleteVacancy(idVacancy: String) {
        dao.delete(idVacancy)
    }

    override suspend fun getVacancyById(idVacancy: String): Flow<VacancyDetail?> = flow {
        val vacancy = dao.getVacancyById(idVacancy)
        if (vacancy != null) {
            emit(vacancyDbConverter.map(vacancy))
        } else {
            emit(null)
        }
    }

    override suspend fun loadVacancies(): Flow<List<Vacancy>> = flow {
        val vacancies = convert(dao.getAll())
        emit(vacancies)
    }

    override suspend fun isFavorite(idVacancy: String): Flow<Boolean> = flow {
        emit(dao.vacancySaved(idVacancy))
    }

    private fun convert(vacancies: List<VacancyEntity>): List<Vacancy> {
        return vacancies.map { vacancy ->
            vacancyDetailToVacancy(vacancyDbConverter.map(vacancy))
        }
    }

    private fun vacancyDetailToVacancy(vacancy: VacancyDetail): Vacancy {
        return Vacancy(
            id = vacancy.id,
            name = vacancy.name,
            employerName = vacancy.employerName,
            employerLogoUrl = vacancy.employerLogo,
            areaName = vacancy.areaName,
            salaryFrom = vacancy.salaryFrom,
            salaryTo = vacancy.salaryTo,
            salaryCurrency = vacancy.salaryCurrency,
        )
    }
}
