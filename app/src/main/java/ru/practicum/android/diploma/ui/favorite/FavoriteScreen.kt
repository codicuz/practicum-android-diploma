package ru.practicum.android.diploma.ui.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.favorite.FavoriteState
import ru.practicum.android.diploma.presentation.favorite.FavoriteViewModel
import ru.practicum.android.diploma.ui.components.NotifyImage
import ru.practicum.android.diploma.ui.components.SimpleScreenTitle
import ru.practicum.android.diploma.ui.components.VacancyItem

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    FavoriteContent(state, {})
}

@Composable
fun FavoriteContent(state: FavoriteState, onItemClick: () -> Unit) {

    Column(modifier = Modifier.padding(16.dp)) {
        SimpleScreenTitle(R.string.favorite_screen_title)

        when (state) {
            is FavoriteState.Loading -> {}
            is FavoriteState.Empty -> NotifyImage(R.drawable.list_empty, R.string.favorite_empty)
            is FavoriteState.Error -> NotifyImage(R.drawable.cat, R.string.favorite_error)
            is FavoriteState.Success -> LazyColumn() {
                items(state.vacancies.size) { index -> VacancyItem(state.vacancies[index], onItemClick) }
            }
        }
    }
}

