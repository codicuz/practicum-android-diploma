package ru.practicum.android.diploma.domain.usecases

import ru.practicum.android.diploma.data.dto.Areas
import ru.practicum.android.diploma.domain.models.AreasRepository

class GetAreasUseCase(
    private val repository: AreasRepository
) {
    suspend operator fun invoke(): Result<List<Areas>> {
        return repository.getAreas()
    }
}
