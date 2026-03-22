package ru.practicum.android.diploma.presentation.filter

fun FilterState.hasActiveFilters(): Boolean {
    return this != FilterState.DEFAULT
}
