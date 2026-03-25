package ru.practicum.android.diploma.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.CountryItemUi
import ru.practicum.android.diploma.ui.filter.CountrySelectContent

val mockCountries = listOf<CountryItemUi>()

@Composable
private fun ScreenPreview() {
    CountrySelectContent(
        title = R.string.country_select,
        onNavigationClick = {},
        countries = mockCountries,
        isLoading = false,
        onCountrySelected = { _, _ -> }
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
