package ru.practicum.android.diploma.domain.models

interface TeamRepository {
    fun getTeamMembers(): List<TeamMember>
}
