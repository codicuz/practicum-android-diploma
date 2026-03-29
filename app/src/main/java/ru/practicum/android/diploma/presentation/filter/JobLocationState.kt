package ru.practicum.android.diploma.presentation.filter

data class JobLocationState(
    val selectedCountryId: Int? = null,
    val selectedCountryName: String? = null,
    val selectedRegionId: Int? = null,
    val selectedRegionName: String? = null,
//    val countries: List<CountryItemUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
