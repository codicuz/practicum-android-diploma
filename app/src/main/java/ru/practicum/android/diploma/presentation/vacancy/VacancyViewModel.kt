package ru.practicum.android.diploma.presentation.vacancy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.FavoriteInteractor
import ru.practicum.android.diploma.domain.api.VacancyInteractor
import ru.practicum.android.diploma.util.Constants.NO_INTERNET_CODE
import ru.practicum.android.diploma.util.Resource

class VacancyViewModel(
    private val vacancyInteractor: VacancyInteractor,
    private val favoriteInteractor: FavoriteInteractor,
) : ViewModel() {

    private val _state = MutableStateFlow<VacancyDetailState>(VacancyDetailState.Loading)
    val state: StateFlow<VacancyDetailState> = _state.asStateFlow()

    fun loadVacancy(vacancyId: String) {
        _state.value = VacancyDetailState.Loading
        viewModelScope.launch {
            val isFavorite = favoriteInteractor.isFavorite(vacancyId).firstOrNull() ?: false

            if (isFavorite) {
                loadFromFavorites(vacancyId)
            } else {
                loadFromNetwork(vacancyId)
            }
        }
    }

    private suspend fun loadFromFavorites(vacancyId: String) {
        favoriteInteractor.getVacancyById(vacancyId).collect { vacancy ->
            if (vacancy != null) {
                _state.value = VacancyDetailState.Content(vacancy)
            }
        }
    }

    private suspend fun loadFromNetwork(vacancyId: String) {
        vacancyInteractor.getVacancyById(vacancyId).collect { result ->
            _state.value = when (result) {
                is Resource.Success -> VacancyDetailState.Content(result.data)
                is Resource.Error -> handleNetworkError(result.code)
            }
        }
    }

    private fun handleNetworkError(code: Int?): VacancyDetailState {
        return if (code == NO_INTERNET_CODE) {
            VacancyDetailState.NoInternet
        } else {
            VacancyDetailState.Error
        }
    }

    fun addVacancyToFavorite() {
        val vacancyDetail = (state.value as? VacancyDetailState.Content)?.vacancy ?: return

        viewModelScope.launch {
            if (vacancyDetail.isFavorite) {
                favoriteInteractor.deleteVacancy(vacancyDetail.id)
            } else {
                favoriteInteractor.addVacancy(vacancyDetail)
            }
            loadVacancy(vacancyDetail.id)
        }
    }
}
