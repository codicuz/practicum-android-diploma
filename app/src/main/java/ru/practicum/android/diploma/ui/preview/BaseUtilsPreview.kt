package ru.practicum.android.diploma.ui.preview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.ui.theme.Theme

@Composable
fun DefaultPreviewContainer(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    Theme(darkTheme = darkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primary
        ) {
            content()
        }
    }
}
