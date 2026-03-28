package ru.practicum.android.diploma.data.dto

data class VacancySearchRequest(
    val text: String,
    val page: Int,
    val perPage: Int,
    )
