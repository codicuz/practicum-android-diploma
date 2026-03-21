package ru.practicum.android.diploma.ui.search

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.search.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel()
){
    SearchContent()
}

@Composable
fun SearchContent(
){
    Text(text = stringResource(R.string.search_screen_title))
}
