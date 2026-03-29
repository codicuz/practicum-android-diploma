package ru.practicum.android.diploma.ui.team

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.team.TeamMemberUi
import ru.practicum.android.diploma.presentation.team.TeamViewModel
import ru.practicum.android.diploma.ui.components.SimpleTopBarTempl

@Composable
fun TeamScreen(
    viewModel: TeamViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    TeamContent(
        title = R.string.team_screen_title,
        contentTitle = R.string.team_screen_content_title,
        members = state.members,
        modifier = Modifier
    )
}

@Composable
fun TeamContent(
    title: Int,
    contentTitle: Int,
    members: List<TeamMemberUi>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            SimpleTopBarTempl(title)

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(contentTitle),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        items(members) { member ->
            Text(
                text = "${stringResource(member.name)} ${stringResource(member.role)}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
