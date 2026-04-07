package ru.practicum.android.diploma.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.VacancyInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancySearchResult
import ru.practicum.android.diploma.presentation.filter.FilterState
import ru.practicum.android.diploma.presentation.filter.FilterViewModel
import ru.practicum.android.diploma.util.Constants
import ru.practicum.android.diploma.util.Constants.NO_INTERNET_CODE
import ru.practicum.android.diploma.util.NetworkConnectivityChecker
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.debounce

@Suppress("LargeClass")
class SearchViewModel(
    private val vacancyInteractor: VacancyInteractor,
    private val filterViewModel: FilterViewModel,
    private val networkChecker: NetworkConnectivityChecker
) : ViewModel() {

    private var currentFilters = FilterState.DEFAULT
    private var lastAppliedFilters = FilterState.DEFAULT

    private val _isNoInternet = MutableStateFlow(false)
    val isNoInternet: StateFlow<Boolean> = _isNoInternet.asStateFlow()

    private val _state = MutableStateFlow<SearchScreenState>(SearchScreenState.Default)
    val state: StateFlow<SearchScreenState> = _state.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _toastEvent = MutableSharedFlow<SearchToastEvent>()
    val toastEvent: SharedFlow<SearchToastEvent> = _toastEvent.asSharedFlow()

    private var currentPage = 0
    private var maxPages = 0
    private var currentVacancies = mutableListOf<Vacancy>()
    private var isNextPageLoading = false

    init {
        viewModelScope.launch {
            filterViewModel.state.collect { filterState ->
                val oldFilters = currentFilters
                currentFilters = filterState

                if (oldFilters != filterState && _searchQuery.value.isNotBlank()) {
                    refreshSearchWithFilters()
                }
            }
        }
    }

    fun applyFiltersAndSearch() {
        val currentQuery = _searchQuery.value
        if (currentQuery.isNotBlank()) {
            lastAppliedFilters = currentFilters
            restartSearch()
        }
    }

    private fun restartSearch() {
        if (!networkChecker.isConnected()) {
            _isNoInternet.value = true
            _state.value = SearchScreenState.NoInternet
            return
        }

        _isNoInternet.value = false
        currentVacancies.clear()
        currentPage = 0
        maxPages = 0
        _state.value = SearchScreenState.Loading
        searchRequest(_searchQuery.value)
    }

    private fun getAreaId(): Int? {
        return currentFilters.selectedRegionId ?: currentFilters.selectedCountryId
    }

    private val searchDebounce = debounce<String>(
        delayMillis = Constants.SEARCH_DEBOUNCE_DELAY_MILLIS,
        coroutineScope = viewModelScope
    ) { query ->
        searchRequest(query)
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            currentVacancies.clear()
            currentPage = 0
            maxPages = 0
            _state.value = SearchScreenState.Default
            lastAppliedFilters = FilterState.DEFAULT
        } else {
            _state.value = SearchScreenState.Loading
            searchDebounce(query)
        }
    }

    fun onClearSearch() {
        _searchQuery.value = ""
        currentVacancies.clear()
        currentPage = 0
        maxPages = 0
        _state.value = SearchScreenState.Default
        lastAppliedFilters = FilterState.DEFAULT
    }

    fun onLoadNextPage() {
        if (!canLoadNextPage()) return

        if (!networkChecker.isConnected()) {
            _isNoInternet.value = true
            viewModelScope.launch {
                _toastEvent.emit(SearchToastEvent.NO_INTERNET)
            }
            return
        }

        startNextPageLoading()
        viewModelScope.launch {
            vacancyInteractor.searchVacancies(
                query = _searchQuery.value,
                page = currentPage + 1,
                perPage = Constants.ITEMS_PER_PAGE,
                salary = currentFilters.salary.toIntOrNull(),
                onlyWithSalary = currentFilters.isWithoutSalayrHidden,
                industry = currentFilters.selectedIndustryId,
                area = getAreaId()
            ).collect { result ->
                handleNextPageResult(result)
            }
        }
    }

    fun refreshSearchWithFilters() {
        val currentQuery = _searchQuery.value
        if (currentQuery.isNotBlank() && currentFilters != lastAppliedFilters) {
            lastAppliedFilters = currentFilters
            currentVacancies.clear()
            currentPage = 0
            maxPages = 0
            _state.value = SearchScreenState.Loading
            searchRequest(currentQuery)
        }
    }

    private fun searchRequest(query: String) {
        viewModelScope.launch {
            if (!networkChecker.isConnected()) {
                _isNoInternet.value = true
                _state.value = SearchScreenState.NoInternet
            } else {
                _isNoInternet.value = false
                currentPage = 0
                currentVacancies.clear()
                vacancyInteractor.searchVacancies(
                    query = query,
                    page = currentPage + 1,
                    perPage = Constants.ITEMS_PER_PAGE,
                    salary = currentFilters.salary.toIntOrNull(),
                    onlyWithSalary = currentFilters.isWithoutSalayrHidden,
                    industry = currentFilters.selectedIndustryId,
                    area = getAreaId()
                ).collect { result ->
                    handleSearchResult(result)
                }
            }
        }
    }

    private fun canLoadNextPage(): Boolean {
        return !isNextPageLoading && currentPage + 1 < maxPages
    }

    private fun startNextPageLoading() {
        isNextPageLoading = true
        val currentState = _state.value
        if (currentState is SearchScreenState.Content) {
            _state.value = currentState.copy(isNextPageLoading = true)
        }
    }

    private suspend fun handleNextPageResult(result: Resource<VacancySearchResult>) {
        when (result) {
            is Resource.Success -> onNextPageSuccess(result.data)
            is Resource.Error -> onNextPageError(result.code)
        }
        isNextPageLoading = false
    }

    private fun onNextPageSuccess(data: VacancySearchResult) {
        currentPage = data.page
        maxPages = data.pages
        currentVacancies.addAll(data.vacancies)
        _state.value = SearchScreenState.Content(
            vacancies = currentVacancies.toList(),
            found = data.found,
            isNextPageLoading = false
        )
    }

    private suspend fun onNextPageError(code: Int?) {
        val currentState = _state.value
        if (currentState is SearchScreenState.Content) {
            _state.value = currentState.copy(isNextPageLoading = false)
        }
        emitToastByCode(code)
    }

    private fun handleSearchResult(result: Resource<VacancySearchResult>) {
        when (result) {
            is Resource.Success -> onSearchSuccess(result.data)
            is Resource.Error -> onSearchError(result.code, result.message)
        }
    }

    private fun onSearchSuccess(data: VacancySearchResult) {
        maxPages = data.pages
        currentVacancies.addAll(data.vacancies)
        _state.value = if (data.vacancies.isEmpty()) {
            SearchScreenState.Empty
        } else {
            SearchScreenState.Content(
                vacancies = currentVacancies.toList(),
                found = data.found
            )
        }
        lastAppliedFilters = currentFilters
    }

    private fun onSearchError(code: Int?, message: String?) {
        _state.value = if (code == NO_INTERNET_CODE) {
            SearchScreenState.NoInternet
        } else {
            SearchScreenState.Error(message)
        }
    }

    private suspend fun emitToastByCode(code: Int?) {
        if (code == NO_INTERNET_CODE) {
            _toastEvent.emit(SearchToastEvent.NO_INTERNET)
        } else {
            _toastEvent.emit(SearchToastEvent.SERVER_ERROR)
        }
    }
}
