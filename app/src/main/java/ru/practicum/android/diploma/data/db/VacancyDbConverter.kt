package ru.practicum.android.diploma.data.db

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.domain.models.PhoneInfo
import ru.practicum.android.diploma.domain.models.VacancyDetail
import kotlin.String

class VacancyDbConverter  {
    val gson = Gson()
    val listStringType = object : TypeToken<List<String>>() {}.type
    val listPhoneInfoType = object : TypeToken<List<PhoneInfo>>() {}.type

    fun map(vacancy: VacancyDetail): VacancyEntity {
        return VacancyEntity(
            id = vacancy.id,
            name = vacancy.name,
            description = vacancy.description,
            employerName = vacancy.employerName,
            employerLogo = vacancy.employerLogo,
            areaName = vacancy.areaName,
            salaryFrom = vacancy.salaryFrom,
            salaryTo = vacancy.salaryTo,
            salaryCurrency = vacancy.salaryCurrency,
            experience = vacancy.experience,
            schedule = vacancy.schedule,
            employment = vacancy.employment,
            address = vacancy.address,
            contactName = vacancy.contactName,
            contactEmail = vacancy.contactEmail,
            contactPhonesJson = gson.toJson(vacancy.contactPhones),
            skillsJson = gson.toJson(vacancy.skills),
            url = vacancy.url,

        )
    }

    fun map(vacancy: VacancyEntity): VacancyDetail {
        return VacancyDetail(
            id = vacancy.id,
            name = vacancy.name,
            description = vacancy.description,
            employerName = vacancy.employerName,
            employerLogo = vacancy.employerLogo,
            areaName = vacancy.areaName,
            salaryFrom = vacancy.salaryFrom,
            salaryTo = vacancy.salaryTo,
            salaryCurrency = vacancy.salaryCurrency,
            experience = vacancy.experience,
            schedule = vacancy.schedule,
            employment = vacancy.employment,
            address = vacancy.address,
            contactName = vacancy.contactName,
            contactEmail = vacancy.contactEmail,
            contactPhones = gson.fromJson(vacancy.contactPhonesJson, listPhoneInfoType),
            skills = gson.fromJson(vacancy.skillsJson, listStringType),
            url = vacancy.url,
            isFavorite = true,
        )
    }
}
