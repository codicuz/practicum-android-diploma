package ru.practicum.android.diploma.data.db

import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyDbConverter {
    fun map(vacancy: Vacancy): VacancyEntity {
        return VacancyEntity(
            id = vacancy.id,
            name = vacancy.name,
            employerName = vacancy.employerName,
            areaName = vacancy.areaName,
            salaryFrom = vacancy.salaryFrom,
            salaryTo = vacancy.salaryTo,
            salaryCurrency = vacancy.salaryCurrency,
            employerLogoUrl = vacancy.employerLogoUrl
        )
    }

    fun map(vacancy: VacancyEntity): Vacancy {
        return Vacancy(
            id = vacancy.id,
            name = vacancy.name,
            employerName = vacancy.employerName,
            areaName = vacancy.areaName,
            salaryFrom = vacancy.salaryFrom,
            salaryTo = vacancy.salaryTo,
            salaryCurrency = vacancy.salaryCurrency,
            employerLogoUrl = vacancy.employerLogoUrl,
            isFavorite = true
        )
    }
}
