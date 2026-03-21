package ru.practicum.android.diploma.domain.usecases

import ru.practicum.android.diploma.domain.models.TeamMember
import ru.practicum.android.diploma.domain.models.TeamRepository

class GetTeamMemberUseCase(private val repository: TeamRepository) {
    operator fun invoke(): List<TeamMember> {
        return repository.getTeamMembers()
    }
}
