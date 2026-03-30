package ru.practicum.android.diploma.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.ui.vacancy.VacancyScreen

@Preview
@Composable
fun VacancyScreenPreviewLight() {
    DefaultPreviewContainer {
        VacancyScreen(
            vacancyId = "123",
            onNavigateBack = {}
        )
    }
}
