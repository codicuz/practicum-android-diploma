package ru.practicum.android.diploma.ui.filter

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filter.FilterViewModel

@Composable
fun FilterScreen(
    viewModel: FilterViewModel = koinViewModel()
){
    FilterContent()
}

@Composable
fun FilterContent(
){
    Text(text = stringResource(R.string.filter_screen_title))
}
