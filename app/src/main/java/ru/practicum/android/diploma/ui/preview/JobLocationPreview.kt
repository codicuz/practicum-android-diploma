package ru.practicum.android.diploma.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filter.JobLocationState
import ru.practicum.android.diploma.ui.filter.JobLocationContent

const val SelectCountryId = 1500

@Composable
private fun ScreenPreview() {
    val mockState = JobLocationState(
        SelectCountryId
    )

    JobLocationContent(
        title = R.string.job_location_select,
        onNavigationClick = {},
        state = mockState,
        onJobCountryClick = {},
        onJobCountryClear = {},
        onJobRegionClick = {},
        onJobRegionClear = {},
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
