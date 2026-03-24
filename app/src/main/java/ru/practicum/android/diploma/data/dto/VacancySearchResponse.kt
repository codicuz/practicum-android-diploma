package ru.practicum.android.diploma.data.dto

data class VacancySearchResponse(
    val items: List<VacancyDto>,
    val found: Int,
    val page: Int,
    val pages: Int,
    val perPage: Int
) : Response()
