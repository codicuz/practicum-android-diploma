package ru.practicum.android.diploma.ui.vacancy

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.vacancy.VacancyViewModel

@Composable
fun VacancyScreen(
    viewModel: VacancyViewModel = koinViewModel()
) {
    VacancyContent()
}

@Composable
fun VacancyContent() {
    Text(text = stringResource(R.string.vacancy_screen_title))
}
