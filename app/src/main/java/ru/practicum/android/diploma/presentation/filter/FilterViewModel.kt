package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.datasources.FilterDataStore

const val DebounceTimer: Long = 200

class FilterViewModel(
    private val filterDataStore: FilterDataStore
) : ViewModel() {
    private val _state = MutableStateFlow(FilterState())
    val state: StateFlow<FilterState> = _state.asStateFlow()

    init {
        loadSavedFilters()
        autoSaveFilters()
    }

    private fun loadSavedFilters() {
        viewModelScope.launch {
            filterDataStore.filterStateFlow
                .collectLatest { savedState ->
                    _state.value = savedState
                }
        }
    }

    @OptIn(FlowPreview::class)
    private fun autoSaveFilters() {
        viewModelScope.launch {
            _state
                .debounce(DebounceTimer)
                .onEach { currentState ->
                    filterDataStore.saveFilterState(currentState)
                }.launchIn(viewModelScope)
        }
    }

    fun resetFilters() {
        viewModelScope.launch {
            filterDataStore.clearFilters()
            _state.value = FilterState.DEFAULT
        }
    }

    fun updateSalary(salary: String) {
        _state.update { currentState ->
            currentState.copy(salary = salary)
        }
    }

    fun updateWithoutSalaryHidden(hidden: Boolean) {
        _state.update { currentState ->
            currentState.copy(isWithoutSalayrHidden = hidden)
        }
    }

    fun updateIndustry(industryId: Int, industryName: String) {
        _state.update { currentState ->
            currentState.copy(
                selectedIndustryId = industryId,
                selectedIndustryName = industryName
            )
        }
    }

    fun clearIndustry() {
        _state.update { currentState ->
            currentState.copy(
                selectedIndustryId = null,
                selectedIndustryName = null
            )
        }
    }

    fun updateCountry(countryId: Int, countryName: String) {
        _state.update { currentState ->
            currentState.copy(
                selectedCountryId = countryId,
                selectedCountryName = countryName
            )
        }
    }

    fun clearCountry() {
        _state.update { currentState ->
            currentState.copy(
                selectedCountryId = null,
                selectedCountryName = null,
                selectedRegionId = null,
                selectedRegionName = null
            )
        }
    }

    fun updateRegion(regionId: Int, regionName: String) {
        _state.update { currentState ->
            currentState.copy(
                selectedRegionId = regionId,
                selectedRegionName = regionName
            )
        }
    }

    fun clearRegion() {
        _state.update { currentState ->
            currentState.copy(
                selectedRegionId = null,
                selectedRegionName = null
            )
        }
    }
}
