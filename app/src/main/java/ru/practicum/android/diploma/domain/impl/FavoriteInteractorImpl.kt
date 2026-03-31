package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.FavoriteInteractor
import ru.practicum.android.diploma.domain.api.FavoriteRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoriteInteractorImpl(
    private val repository: FavoriteRepository
): FavoriteInteractor {

    suspend override fun loadVacancies(): Flow<List<Vacancy>> {
        return repository.loadVacancies()
    }

    suspend override fun deleteVacancy(vacancy: Vacancy) {
        repository.deleteVacancy(vacancy)
    }

    suspend override fun addVacancy(vacancy: Vacancy) {
        repository.addVacancy(vacancy)
    }
}
