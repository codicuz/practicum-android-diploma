package ru.practicum.android.diploma.data.repositories

import ru.practicum.android.diploma.data.datasources.LocalTeamDataSource
import ru.practicum.android.diploma.domain.models.TeamMember
import ru.practicum.android.diploma.domain.models.TeamRepository

class TeamRepositoryImpl(private val localDataSource: LocalTeamDataSource) : TeamRepository {
    override fun getTeamMembers(): List<TeamMember> {
        return localDataSource.getTeamMembers()
    }
}
