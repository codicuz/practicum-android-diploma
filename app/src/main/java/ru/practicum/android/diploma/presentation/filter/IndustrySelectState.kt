package ru.practicum.android.diploma.presentation.filter

import ru.practicum.android.diploma.domain.models.IndustryItemUi

data class IndustrySelectState(
    val industries: List<IndustryItemUi> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedIndustryId: Int? = null
) {
    val filteredIndustries: List<IndustryItemUi>
        get() = industries
}
