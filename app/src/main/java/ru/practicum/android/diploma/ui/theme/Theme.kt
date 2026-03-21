package ru.practicum.android.diploma.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


data class AdditionalColors(
    val cursor: Color,
    val placeHolder: Color,
    val gray: Color,
    val lightGray: Color,
    val blue: Color,
    val red: Color,
    val black: Color,
    val white: Color
)

val LocalAdditionalColors = staticCompositionLocalOf {
    AdditionalColors(
        cursor = Color.Unspecified,
        placeHolder = Color.Unspecified,
        gray = Color.Unspecified,
        lightGray = Color.Unspecified,
        blue = Color.Unspecified,
        red = Color.Unspecified,
        black = Color.Unspecified,
        white = Color.Unspecified
    )
}

private val DarkColorScheme = darkColorScheme(
    primary = blackUniversal,
    onPrimary = whiteUniversal,
    surface = gray,
    onSurface = gray,
    surfaceVariant = lightGray,
    outlineVariant = lightGray

)

private val LightColorScheme = lightColorScheme(
    primary = whiteUniversal,
    onPrimary = blackUniversal,
    surface = lightGray,
    onSurface = gray,
    surfaceVariant = lightGray,
    outlineVariant = lightGray
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val additionalColors = AdditionalColors(
        cursor = if (darkTheme) blue else blue,
        placeHolder = if (darkTheme) whiteUniversal else gray,
        gray = gray,
        lightGray = lightGray,
        blue = blue,
        red = red,
        black = blackUniversal,
        white = whiteUniversal
    )

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    CompositionLocalProvider(
        LocalAdditionalColors provides additionalColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

val MaterialTheme.additionalColors: AdditionalColors
    @Composable
    @ReadOnlyComposable
    get() = LocalAdditionalColors.current
