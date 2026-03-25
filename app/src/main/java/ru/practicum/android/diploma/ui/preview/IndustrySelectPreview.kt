package ru.practicum.android.diploma.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filter.IndustrySelectState
import ru.practicum.android.diploma.ui.filter.IndustrySelectContent

@Composable
private fun ScreenPreview() {
    IndustrySelectContent(
        title = R.string.industry_select,
        onNavigationClick = {},
        state = IndustrySelectState(),
        onSearchQueryChange = {},
        onIndustrySelected = {},
        onConfirmClick = {}
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
