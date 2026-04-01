package ru.practicum.android.diploma.presentation.vacancy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
            favoriteInteractor.isFavorite(idVacancy = vacancyId).collect { result ->
                if (result) favoriteInteractor.getVacancyById(vacancyId).collect { result ->
                    if (result != null) _state.value = VacancyDetailState.Content(result)
                } else {

                    vacancyInteractor.getVacancyById(vacancyId).collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                _state.value = VacancyDetailState.Content(result.data)
                            }

                            is Resource.Error -> {
                                _state.value = if (result.code == NO_INTERNET_CODE) {
                                    VacancyDetailState.NoInternet
                                } else {
                                    VacancyDetailState.Error
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun addVacancyToFavorite(){
        if (state.value is VacancyDetailState.Content) {
            val vacancyDetail = (state.value as VacancyDetailState.Content).vacancy
            if (vacancyDetail.isFavorite) {
                viewModelScope.launch {
                    favoriteInteractor.deleteVacancy(vacancyDetail.id)
                    loadVacancy(vacancyDetail.id)
                }
            } else {
                viewModelScope.launch {
                    favoriteInteractor.addVacancy(vacancyDetail)
                    loadVacancy(vacancyDetail.id)
                }
            }
        }
    }

}
