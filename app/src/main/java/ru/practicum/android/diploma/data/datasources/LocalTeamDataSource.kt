package ru.practicum.android.diploma.data.datasources

import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.TeamMember

class LocalTeamDataSource {
    fun getTeamMembers(): List<TeamMember> {
        return listOf(
            TeamMember(
                nameResId = R.string.team_screen_content_item_name_1,
                roleResId = R.string.team_screen_content_item_role_1
            ),
            TeamMember(
                nameResId = R.string.team_screen_content_item_name_2,
                roleResId = R.string.team_screen_content_item_role_2
            ),
            TeamMember(
                nameResId = R.string.team_screen_content_item_name_3,
                roleResId = R.string.team_screen_content_item_role_3
            ),
//            TeamMember(
//                nameResId = R.string.team_screen_content_item_name_4,
//                roleResId = R.string.team_screen_content_item_role_4
//            ),
        )
    }
}
