package ru.practicum.android.diploma.util

import android.icu.text.DecimalFormat
import ru.practicum.android.diploma.ui.components.GrpSize

fun formatNumberWithSpaces(number: Int): String {
    val formatter = DecimalFormat("#,###").apply {
        groupingSize = GrpSize
    }
    return formatter.format(number).replace(',', ' ')
}
