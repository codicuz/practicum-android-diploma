package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.db.VacanciesDao
import ru.practicum.android.diploma.data.db.VacancyDbConverter
import ru.practicum.android.diploma.data.db.VacancyEntity
import ru.practicum.android.diploma.domain.api.FavoriteRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoriteRepositoryImpl(
    private val dao: VacanciesDao,
    private val vacancyDbConverter: VacancyDbConverter
): FavoriteRepository {

    suspend override fun addVacancy(vacancy: Vacancy) {
        dao.insert(vacancyDbConverter.map(vacancy))
    }

    suspend override fun deleteVacancy(vacancy: Vacancy) {
        dao.delete(vacancyDbConverter.map(vacancy))
    }

    suspend override fun loadVacancies(): Flow<List<Vacancy>> = flow {
        val vacancies = convert(dao.getAll())
        emit(vacancies)
    }

    private fun convert(vacancies: List<VacancyEntity>): List<Vacancy> {
        return vacancies.map {vacancy -> vacancyDbConverter.map(vacancy)}
    }
}

