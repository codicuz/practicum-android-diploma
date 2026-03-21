package ru.practicum.android.diploma.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.ui.root.Greeting
import ru.practicum.android.diploma.ui.theme.Theme

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun GreetingPreview() {
    Theme(darkTheme = false) {
        Greeting("Android")
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun GreetingPreviewDark() {
    Theme(darkTheme = true) {
        Greeting("Android")
    }
}
