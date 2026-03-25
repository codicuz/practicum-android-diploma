package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.CountryItemUi
import ru.practicum.android.diploma.domain.models.RegionItemUi
import ru.practicum.android.diploma.domain.usecases.GetAreasUseCase

class JobLocationViewModel(
    private val getAreasUseCase: GetAreasUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(JobLocationState())
    val state: StateFlow<JobLocationState> = _state.asStateFlow()

    private val _areas = MutableStateFlow<List<CountryItemUi>>(emptyList())
    val areas = _areas.asStateFlow()

    private val _regions = MutableStateFlow<List<RegionItemUi>>(emptyList())
    val regions = _regions.asStateFlow()

    private val _isRegionsLoading = MutableStateFlow(false)
    val isRegionsLoading: StateFlow<Boolean> = _isRegionsLoading.asStateFlow()

    private val _isCountriesLoading = MutableStateFlow(false)
    val isCountriesLoading: StateFlow<Boolean> = _isCountriesLoading.asStateFlow()

    fun loadAreas() {
        viewModelScope.launch {
            _isCountriesLoading.value = true
            _state.update { it.copy(isLoading = true, error = null) }

            val result = getAreasUseCase()

            result.onSuccess { areas ->
                _areas.value = areas.map { country ->
                    CountryItemUi(
                        id = country.id,
                        name = country.name,
                        regions = country.areas.map { region ->
                            RegionItemUi(
                                id = region.id,
                                parentId = region.parentId,
                                name = region.name
                            )
                        },
                    )
                }

                _state.update { it.copy(isLoading = false) }

            }.onFailure { error ->
                _state.update { it.copy(isLoading = false, error = error.message ?: "Unknown error") }
            }
            _isCountriesLoading.value = false
        }
    }

    fun loadRegions(countryId: Int? = null) {
        viewModelScope.launch {
            _isRegionsLoading.value = true

            if (_areas.value.isEmpty()) {
                loadAreasAndThenRegions(countryId)
            } else {
                updateRegionsList(countryId)
                _isRegionsLoading.value = false
            }
        }
    }

    private suspend fun loadAreasAndThenRegions(countryId: Int? = null) {
        val result = getAreasUseCase()

        result.onSuccess { areas ->
            _areas.value = areas.map { country ->
                CountryItemUi(
                    id = country.id,
                    name = country.name,
                    regions = country.areas.map { region ->
                        RegionItemUi(
                            id = region.id,
                            parentId = region.parentId,
                            name = region.name
                        )
                    },
                )
            }
            updateRegionsList(countryId)

        }.onFailure { _ ->
            _regions.value = emptyList()
        }

        _isRegionsLoading.value = false
    }

    private fun updateRegionsList(countryId: Int? = null) {
        val regionsList = if (countryId != null) {
            _areas.value
                .firstOrNull { it.id == countryId }
                ?.regions
                ?: emptyList()
        } else {
            _areas.value.flatMap { country ->
                country.regions.map { region ->
                    RegionItemUi(
                        id = region.id,
                        parentId = region.parentId,
                        name = region.name
                    )
                }
            }.sortedBy { it.name }
        }

        _regions.value = regionsList
    }

    fun getRegions(countryId: Int? = null): List<RegionItemUi> {
        return if (countryId != null) {
            _areas.value
                .firstOrNull { it.id == countryId }
                ?.regions
                ?: emptyList()
        } else {
            _areas.value.flatMap { it.regions }
        }
    }

    fun selectCountry(countryId: Int, countryName: String) {
        _state.update { currentState ->
            currentState.copy(
                selectedCountryId = countryId,
                selectedCountryName = countryName,
                selectedRegionId = null,
                selectedRegionName = null
            )
        }
    }

    fun selectRegion(regionId: Int, regionName: String) {
        _state.update { currentState ->
            currentState.copy(
                selectedRegionId = regionId,
                selectedRegionName = regionName
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

    fun clearRegion() {
        _state.update { currentState ->
            currentState.copy(
                selectedRegionId = null,
                selectedRegionName = null
            )
        }
    }
}
