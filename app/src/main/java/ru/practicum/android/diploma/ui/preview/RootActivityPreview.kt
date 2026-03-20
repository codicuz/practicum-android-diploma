package ru.practicum.android.diploma.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.ui.root.Greeting
import ru.practicum.android.diploma.ui.theme.AppTheme

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun GreetingPreview() {
    AppTheme(darkTheme = false) {
        Greeting("Android")
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun GreetingPreviewDark() {
    AppTheme(darkTheme = true) {
        Greeting("Android")
    }
}
