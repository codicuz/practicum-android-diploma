package ru.practicum.android.diploma.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filter.FilterState
import ru.practicum.android.diploma.ui.filter.FilterContent

@Composable
private fun ScreenPreview() {
    val mockState = FilterState(
        "1500"
    )
    FilterContent(
        title = R.string.filter_settings,
        showResetButton = true,
        onNavigationClick = {},
        onJobLocationClick = {},
        onIndustryClick = {},
        state = mockState,
        onSalaryChange = {},
        onWithoutSalaryHidden = {},
        onResetButtonClick = {},
        onIndustryClear = {},
        onCountryClear = {},
        onRegionClear = {},
        onApplyClick = {}
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
