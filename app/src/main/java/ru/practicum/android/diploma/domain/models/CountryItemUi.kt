package ru.practicum.android.diploma.domain.models

data class CountryItemUi(
    val id: Int,
    val name: String,
    val regions: List<RegionItemUi>
)
