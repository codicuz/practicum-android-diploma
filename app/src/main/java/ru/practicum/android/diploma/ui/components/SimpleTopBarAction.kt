package ru.practicum.android.diploma.ui.components

import androidx.compose.ui.graphics.Color

data class SimpleTopBarAction(
    val icon: Int,
    val contentDescription: String,
    val onClick: () -> Unit,
    val tint: Color? = null
)
