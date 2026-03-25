package ru.practicum.android.diploma.domain.models

import ru.practicum.android.diploma.data.dto.Industries

interface IndustriesRepository {
    suspend fun getIndustries(): Result<List<Industries>>
}
