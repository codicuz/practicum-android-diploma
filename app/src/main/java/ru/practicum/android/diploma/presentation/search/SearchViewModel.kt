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
import ru.practicum.android.diploma.data.network.NO_INTERNET_CODE
import ru.practicum.android.diploma.domain.api.VacancyInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Constants
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
    }

    fun onLoadNextPage() {
        if (isNextPageLoading) return
        if (currentPage + 1 >= maxPages) return

        isNextPageLoading = true
        val currentState = _state.value
        if (currentState is SearchScreenState.Content) {
            _state.value = currentState.copy(isNextPageLoading = true)
        }

        viewModelScope.launch {
            vacancyInteractor.searchVacancies(       // ← ИСПРАВЛЕНО
                query = _searchQuery.value,
                page = currentPage + 1,
                perPage = Constants.ITEMS_PER_PAGE
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        currentPage = result.data.page
                        maxPages = result.data.pages
                        currentVacancies.addAll(result.data.vacancies)
                        _state.value = SearchScreenState.Content(
                            vacancies = currentVacancies.toList(),
                            found = result.data.found,
                            isNextPageLoading = false
                        )
                    }

                    is Resource.Error -> {
                        if (currentState is SearchScreenState.Content) {
                            _state.value = currentState.copy(isNextPageLoading = false)
                        }
                        if (result.code == NO_INTERNET_CODE) {
                            _toastEvent.emit(SearchToastEvent.NO_INTERNET)
                        } else {
                            _toastEvent.emit(SearchToastEvent.SERVER_ERROR)
                        }
                    }
                }
                isNextPageLoading = false
            }
        }
    }

    private fun searchRequest(query: String) {
        viewModelScope.launch {
            currentPage = 0
            currentVacancies.clear()

            vacancyInteractor.searchVacancies(       // ← ИСПРАВЛЕНО
                query = query,
                page = currentPage + 1,
                perPage = Constants.ITEMS_PER_PAGE
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val data = result.data
                        maxPages = data.pages
                        currentVacancies.addAll(data.vacancies)

                        if (data.vacancies.isEmpty()) {
                            _state.value = SearchScreenState.Empty
                        } else {
                            _state.value = SearchScreenState.Content(
                                vacancies = currentVacancies.toList(),
                                found = data.found
                            )
                        }
                    }

                    is Resource.Error -> {
                        if (result.code == NO_INTERNET_CODE) {
                            _state.value = SearchScreenState.NoInternet
                        } else {
                            _state.value = SearchScreenState.Error(result.message)
                        }
                    }
                }
            }
        }
    }
}
