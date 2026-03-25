package ru.practicum.android.diploma.domain.usecases

import ru.practicum.android.diploma.data.dto.Industries
import ru.practicum.android.diploma.domain.models.IndustriesRepository

class GetIndustriesUseCase(
    private val repository: IndustriesRepository
) {
    suspend operator fun invoke(): Result<List<Industries>> {
        return repository.getIndustries()
    }
}
