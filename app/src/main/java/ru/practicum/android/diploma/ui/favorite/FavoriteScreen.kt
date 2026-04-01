package ru.practicum.android.diploma.ui.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
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
    viewModel: FavoriteViewModel = koinViewModel(),
    onVacancyClick: (String) -> Unit = {},
) {

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.reloadData()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    FavoriteContent(state, onVacancyClick)

}

@Composable
fun FavoriteContent(state: FavoriteState, onItemClick: (String) -> Unit) {

    Column(modifier = Modifier.padding(16.dp)) {
        SimpleScreenTitle(R.string.favorite_screen_title)

        when (state) {
            is FavoriteState.Loading -> {}
            is FavoriteState.Empty -> NotifyImage(R.drawable.list_empty, R.string.favorite_empty)
            is FavoriteState.Error -> NotifyImage(R.drawable.cat, R.string.favorite_error)
            is FavoriteState.Success -> LazyColumn() {
                items(state.vacancies.size) {
                    index -> VacancyItem(
                        vacancy = state.vacancies[index],
                        onClick = {onItemClick(state.vacancies[index].id)}
                    )
                }
            }
        }
    }
}

