package ru.practicum.android.diploma.presentation.vacancy

import ru.practicum.android.diploma.domain.models.VacancyDetail

sealed interface VacancyDetailState {
    data object Loading : VacancyDetailState
    data class Content(val vacancy: VacancyDetail) : VacancyDetailState
    data object Error : VacancyDetailState
    data object NoInternet : VacancyDetailState
}
