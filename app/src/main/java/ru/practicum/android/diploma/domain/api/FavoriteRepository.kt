package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface FavoriteRepository {
    suspend fun loadVacancies(): Flow<List<Vacancy>>
    suspend fun addVacancy(vacancy: Vacancy)
    suspend fun deleteVacancy(vacancy: Vacancy)
}
