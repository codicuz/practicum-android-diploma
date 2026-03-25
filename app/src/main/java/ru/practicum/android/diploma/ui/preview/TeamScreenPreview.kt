package ru.practicum.android.diploma.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.team.TeamMemberUi
import ru.practicum.android.diploma.ui.team.TeamContent

@Composable
private fun ScreenPreview() {
    val members = remember {
        listOf(
            TeamMemberUi(
                R.string.team_screen_content_item_name_1,
                R.string.team_screen_content_item_role_1
            ),
            TeamMemberUi(
                R.string.team_screen_content_item_name_2,
                R.string.team_screen_content_item_role_2
            ),
            TeamMemberUi(
                R.string.team_screen_content_item_name_3,
                R.string.team_screen_content_item_role_3
            ),
//            TeamMemberUi(
//                R.string.team_screen_content_item_name_4,
//                R.string.team_screen_content_item_role_4
//            )
        )
    }

    TeamContent(
        title = R.string.team_screen_title,
        contentTitle = R.string.team_screen_content_title,
        members = members
    )
}

@Preview
@Composable
private fun ScreenPreviewLight() {
    DefaultPreviewContainer {
        ScreenPreview()
    }
}

@Preview
@Composable
private fun ScreenPreviewDark() {
    DefaultPreviewContainer(darkTheme = true) {
        ScreenPreview()
    }
}
