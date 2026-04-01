package ru.practicum.android.diploma.presentation.favorite

import ru.practicum.android.diploma.domain.models.Vacancy

sealed class FavoriteState {
    data object Loading : FavoriteState()
    data object Empty : FavoriteState()
    data object Error : FavoriteState()
    data class Success(
        val vacancies: List<Vacancy>
    ) : FavoriteState()
}
