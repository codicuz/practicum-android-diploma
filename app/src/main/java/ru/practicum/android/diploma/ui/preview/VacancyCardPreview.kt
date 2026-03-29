package ru.practicum.android.diploma.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.components.VacancyCardContent

val mockVacancy = Vacancy(
    id = "1",
    name = "Name",
    employerName = "EmplName",
    employerLogoUrl = "https://...",
    areaName = "areaName",
    salaryFrom = 0,
    salaryTo = 1,
    salaryCurrency = "1500"
)

@Composable
private fun ScreenPreview() {

    VacancyCardContent(
        vacancy = mockVacancy,
        onClick = {}
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
