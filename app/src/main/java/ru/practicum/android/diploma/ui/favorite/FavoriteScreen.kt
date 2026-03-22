package ru.practicum.android.diploma.ui.favorite

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.favorite.FavoriteViewModel

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = koinViewModel()
) {
    FavoriteContent()
}

@Composable
fun FavoriteContent() {
    Text(text = stringResource(R.string.favorite_screen_title))
}
