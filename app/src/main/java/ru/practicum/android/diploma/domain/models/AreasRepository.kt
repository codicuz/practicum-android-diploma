package ru.practicum.android.diploma.domain.models

import ru.practicum.android.diploma.data.dto.Areas

interface AreasRepository {
    suspend fun getAreas(): Result<List<Areas>>
}
