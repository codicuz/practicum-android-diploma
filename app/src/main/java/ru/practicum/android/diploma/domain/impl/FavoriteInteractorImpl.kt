package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.FavoriteInteractor
import ru.practicum.android.diploma.domain.api.FavoriteRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetail

class FavoriteInteractorImpl(
    private val repository: FavoriteRepository
) : FavoriteInteractor {

    override suspend fun loadVacancies(): Flow<List<Vacancy>> {
        return repository.loadVacancies()
    }

    override suspend fun deleteVacancy(idVacancy: String) {
        repository.deleteVacancy(idVacancy)
    }

    override suspend fun addVacancy(vacancy: VacancyDetail) {
        repository.addVacancy(vacancy)
    }

    override suspend fun isFavorite(idVacancy: String): Flow<Boolean> {
        return repository.isFavorite(idVacancy)
    }

    override suspend fun getVacancyById(idVacancy: String): Flow<VacancyDetail?> {
        return repository.getVacancyById(idVacancy)
    }
}
