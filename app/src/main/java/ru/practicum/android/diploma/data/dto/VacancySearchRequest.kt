package ru.practicum.android.diploma.data.dto

data class VacancySearchRequest(
    val text: String,
    val page: Int,
    val perPage: Int,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false,
    val industry: Int? = null,
    val area: Int? = null
)
