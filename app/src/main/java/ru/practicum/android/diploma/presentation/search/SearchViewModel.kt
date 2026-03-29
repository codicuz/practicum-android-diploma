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
import ru.practicum.android.diploma.util.Constants
import ru.practicum.android.diploma.util.Constants.NO_INTERNET_CODE
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(
    private val vacancyInteractor: VacancyInteractor
) : ViewModel() {

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
        resetSearch()

    }

    fun onLoadNextPage() {
        if (!canLoadNextPage()) return
        startNextPageLoading()
        viewModelScope.launch {
            vacancyInteractor.searchVacancies(
                query = _searchQuery.value,
                page = currentPage + 1,
                perPage = Constants.ITEMS_PER_PAGE
            ).collect { result ->
                handleNextPageResult(result)
            }
        }
    }

    private fun searchRequest(query: String) {
        viewModelScope.launch {
            currentPage = 0
            currentVacancies.clear()
            vacancyInteractor.searchVacancies(
                query = query,
                page = currentPage + 1,
                perPage = Constants.ITEMS_PER_PAGE
            ).collect { result ->
                handleSearchResult(result)
            }
        }
    }

    private fun resetSearch() {
        currentVacancies.clear()
        currentPage = 0
        maxPages = 0
        _state.value = SearchScreenState.Default
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
