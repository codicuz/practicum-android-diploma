package ru.practicum.android.diploma.domain.models

data class Vacancy(
    val id: String,
    val name: String,
    val employerName: String,
    val employerLogoUrl: String?,
    val areaName: String,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryCurrency: String?,
    var isFavorite: Boolean = false
)
