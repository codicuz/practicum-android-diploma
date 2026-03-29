package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyDto(
    val id: String,
    val name: String,
    val salary: SalaryDto?,
    val employer: EmployerDto?,
    val area: AreaDto?
)

data class SalaryDto(
    val from: Int?,
    val to: Int?,
    val currency: String?
)

data class EmployerDto(
    val name: String?,
    @SerializedName("logo_urls")
    val logoUrls: LogoUrlsDto?
)

data class LogoUrlsDto(
    @SerializedName("90")
    val logo90: String?,
    @SerializedName("240")
    val logo240: String?,
    val original: String?
)

data class AreaDto(
    val id: String?,
    val name: String?
)
