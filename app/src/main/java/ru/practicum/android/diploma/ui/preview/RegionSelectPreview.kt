package ru.practicum.android.diploma.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.RegionItemUi
import ru.practicum.android.diploma.ui.filter.RegionSelectContent

val mockRegions = listOf<RegionItemUi>(
    RegionItemUi(id = 1, parentId = 1, name = "Регион 1"),
    RegionItemUi(id = 1, parentId = 1, name = "Регион 2"),
    RegionItemUi(id = 1, parentId = 1, name = "Регион 3"),
    RegionItemUi(id = 1, parentId = 1, name = "Регион 4")
)

@Composable
private fun ScreenPreview() {
    RegionSelectContent(
        title = R.string.region_select,
        onNavigationClick = {},
        regions = mockRegions,
        isLoading = false,
        onRegionSelected = { _, _ -> }
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
