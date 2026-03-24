package ru.practicum.android.diploma.presentation.search

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface SearchScreenState {
    data object Default : SearchScreenState
    data object Loading : SearchScreenState
    data class Content(
        val vacancies: List<Vacancy>,
        val found: Int,
        val isNextPageLoading: Boolean = false
    ) : SearchScreenState
    data object Empty : SearchScreenState
    data object NoInternet : SearchScreenState
    data class Error(val message: String) : SearchScreenState
}
