package ru.practicum.android.diploma.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.FavoriteInteractor

class FavoriteViewModel(private val favoriteInteractor: FavoriteInteractor) : ViewModel() {
    private val _uiState = MutableStateFlow<FavoriteState>(FavoriteState.Loading)
    val uiState: StateFlow<FavoriteState> = _uiState.asStateFlow()

    init {
//
//        val vacancies = dummyVacancies()
//        viewModelScope.launch {
//        vacancies.forEach {
//
//                favoriteInteractor.addVacancy(it)
//        }}
//
        loadFavoriteVacancie()
    }

    fun reloadData() {
        loadFavoriteVacancie()
    }

    private fun loadFavoriteVacancie() {
        viewModelScope.launch {
            favoriteInteractor.loadVacancies().collect { vacancies ->
                if (vacancies.isEmpty()) {
                    _uiState.value = FavoriteState.Empty
                } else {
                    _uiState.value = FavoriteState.Success(vacancies)
                }
            }
        }
    }

}
