package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetail

interface FavoriteInteractor {
    suspend fun loadVacancies(): Flow<List<Vacancy>>
    suspend fun addVacancy(vacancy: VacancyDetail)
    suspend fun deleteVacancy(idVacancy: String)
    suspend fun isFavorite(idVacancy: String): Flow<Boolean>
    suspend fun getVacancyById(idVacancy: String): Flow<VacancyDetail?>
}
