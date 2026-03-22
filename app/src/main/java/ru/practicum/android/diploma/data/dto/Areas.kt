package ru.practicum.android.diploma.data.dto

data class Areas(
    val id: Int,
    val name: String,
    val parentId: Int,
    val areas: List<Areas>
)
