package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.IndustryItemUi
import ru.practicum.android.diploma.domain.usecases.GetIndustriesUseCase

class IndustrySelectViewModel(
    private val getIndustriesUseCase: GetIndustriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(IndustrySelectState())
    val state: StateFlow<IndustrySelectState> = _state.asStateFlow()

    private val _industries = MutableStateFlow<List<IndustryItemUi>>(emptyList())
    val industries = _industries.asStateFlow()

    fun loadIndustries() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val result = getIndustriesUseCase()

            result.onSuccess { industries ->
                _industries.value = industries.map { industry ->
                    IndustryItemUi(
                        id = industry.id,
                        name = industry.name
                    )
                }

                updateFilteredIndustries(_state.value.searchQuery)

                _state.update {
                    it.copy(isLoading = false)
                }

            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = error.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    fun updateTextFilterField(query: String) {
        _state.update {
            it.copy(searchQuery = query)
        }
        updateFilteredIndustries(query)
    }

    fun selectIndustry(industryId: Int) {
        _state.update { it.copy(selectedIndustryId = industryId) }
    }

    private fun updateFilteredIndustries(query: String) {
        val filtered = if (query.isEmpty()) {
            _industries.value
        } else {
            _industries.value.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
        _state.update {
            it.copy(
                industries = filtered,
            )
        }
    }
}
