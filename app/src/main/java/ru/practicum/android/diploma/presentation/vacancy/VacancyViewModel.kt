package ru.practicum.android.diploma.presentation.vacancy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.VacancyInteractor
import ru.practicum.android.diploma.util.Constants.NO_INTERNET_CODE
import ru.practicum.android.diploma.util.Resource

class VacancyViewModel(
    private val vacancyInteractor: VacancyInteractor
) : ViewModel() {

    private val _state = MutableStateFlow<VacancyDetailState>(VacancyDetailState.Loading)
    val state: StateFlow<VacancyDetailState> = _state.asStateFlow()

    fun loadVacancy(vacancyId: String) {
        _state.value = VacancyDetailState.Loading
        viewModelScope.launch {
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
