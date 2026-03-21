package ru.practicum.android.diploma.ui.theme


import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.practicum.android.diploma.R

val YsDisplay = FontFamily(
    Font(R.font.ys_display_bold, weight = FontWeight.Bold),
    Font(R.font.ys_display_medium, weight = FontWeight.Medium),
    Font(R.font.ys_display_regular, weight = FontWeight.Normal),
    Font(R.font.ys_display_light, weight = FontWeight.Light),
)
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = YsDisplay,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 38.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = YsDisplay,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp
    ),
    titleSmall = TextStyle(
        fontFamily = YsDisplay,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 19.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = YsDisplay,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 38.sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = YsDisplay,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 19.sp,
        letterSpacing = 0.sp
    ),
    bodySmall = TextStyle(
        fontFamily = YsDisplay,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    )
)
