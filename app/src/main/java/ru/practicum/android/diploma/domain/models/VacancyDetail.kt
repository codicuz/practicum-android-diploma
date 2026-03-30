package ru.practicum.android.diploma.domain.models

data class VacancyDetail(
    val id: String,
    val name: String,
    val description: String,
    val employerName: String,
    val employerLogo: String?,
    val areaName: String,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryCurrency: String?,
    val experience: String?,
    val schedule: String?,
    val employment: String?,
    val address: String?,
    val contactName: String?,
    val contactEmail: String?,
    val contactPhones: List<PhoneInfo>,
    val skills: List<String>,
    val url: String?
)
data class PhoneInfo(
    val formatted: String,
    val comment: String?
)
