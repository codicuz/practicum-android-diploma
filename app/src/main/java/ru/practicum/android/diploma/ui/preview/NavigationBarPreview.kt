package ru.practicum.android.diploma.ui.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.navigation.compose.rememberNavController
import ru.practicum.android.diploma.ui.navigation.NavBottomBar

const val MaxHeight = 0.5f

@PreviewScreenSizes
@Composable
fun ScreenPreview() {
    val navController = rememberNavController()
    DefaultPreviewContainer {
        Column {
            Spacer(modifier = Modifier.fillMaxHeight(MaxHeight))
            NavBottomBar(navController = navController)
        }
    }
}

@Preview
@Composable
private fun ScreenPreviewLight() {
    val navController = rememberNavController()
    DefaultPreviewContainer {
        NavBottomBar(
            navController = navController
        )
    }
}

@Preview
@Composable
private fun ScreenPreviewDark() {
    val navController = rememberNavController()
    DefaultPreviewContainer(darkTheme = true) {
        NavBottomBar(
            navController = navController
        )
    }
}
