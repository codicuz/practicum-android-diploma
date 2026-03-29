package ru.practicum.android.diploma.data.dto

data class VacancyDto(
    val id: String,
    val name: String,
    val salary: SalaryDto?,
    val employer: EmployerDto?,
    val area: AreaDto?,
    val description: String? = null,
    val address: AddressDto? = null,
    val experience: ExperienceDto? = null,
    val schedule: ScheduleDto? = null,
    val employment: EmploymentDto? = null,
    val contacts: ContactsDto? = null,
    val skills: List<String>? = null,
    val url: String? = null,
    val industry: IndustryRefDto? = null
)

data class SalaryDto(
    val from: Int?,
    val to: Int?,
    val currency: String?
)

data class EmployerDto(
    val id: String,
    val name: String,
    val logo: String
)

data class AreaDto(
    val id: String?,
    val name: String?
)

data class AddressDto(
    val city: String?,
    val street: String?,
    val building: String?,
    val fullAddress: String?
)

data class ExperienceDto(
    val id: String?,
    val name: String?
)

data class ScheduleDto(
    val id: String?,
    val name: String?
)

data class EmploymentDto(
    val id: String?,
    val name: String?
)

data class ContactsDto(
    val id: String?,
    val name: String?,
    val email: String?,
    val phone: List<String>?
)

data class IndustryRefDto(
    val id: Int?,
    val name: String?
)
