package ru.practicum.android.diploma.ui.components

data class SimpleTopBarAction(
    val icon: Int,
    val contentDescription: String,
    val onClick: () -> Unit
)
