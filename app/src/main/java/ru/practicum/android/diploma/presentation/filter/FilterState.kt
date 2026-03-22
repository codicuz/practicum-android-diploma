package ru.practicum.android.diploma.presentation.filter

data class FilterState(
    val salary: String = "",
    val isWithoutSalayrHidden: Boolean = false,
    val selectedCountryId: Int? = null,
    val selectedCountryName: String? = null,
    val selectedRegionId: Int? = null,
    val selectedRegionName: String? = null,
    val selectedIndustryId: Int? = null,
    val selectedIndustryName: String? = null
) {
    companion object {
        val DEFAULT = FilterState()
    }
}
