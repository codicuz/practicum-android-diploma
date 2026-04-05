package ru.practicum.android.diploma.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancies")
data class VacancyEntity(
    @PrimaryKey(autoGenerate = true) val vId: Long,
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
    val contactPhonesJson: String,
    val skillsJson: String,
    val url: String?
)
