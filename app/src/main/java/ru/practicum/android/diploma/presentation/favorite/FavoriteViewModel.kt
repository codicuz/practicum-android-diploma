package ru.practicum.android.diploma.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.FavoriteInteractor
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoriteViewModel(private val favoriteInteractor: FavoriteInteractor) : ViewModel(){
    private val _uiState = MutableStateFlow<FavoriteState>(FavoriteState.Loading)
    val uiState: StateFlow<FavoriteState> = _uiState.asStateFlow()

    init {

        val vacancies = dummyVacancies()
        viewModelScope.launch {
        vacancies.forEach {

                favoriteInteractor.addVacancy(it)
        }}


        loadFavoriteVacancie()
    }

    private fun loadFavoriteVacancie(){
        viewModelScope.launch {
            favoriteInteractor.loadVacancies().collect { vacancies ->
                if (vacancies.isEmpty()) _uiState.value = FavoriteState.Empty
                else _uiState.value = FavoriteState.Success(vacancies)
            }
        }
    }

}





fun dummyVacancies(): List<Vacancy>{
    return listOf(
        Vacancy("001", "менеджер по клинингу", "ООО 'Рюмашка'", null, "Россия", 10000, 10000, "RUB" ),
        Vacancy("002", "старший помощник младшего дворника", "ООО 'Рюмашка'", null, "Россия", 20000, 30000, "RUB" ),
        Vacancy("003", "специалист ОТК", "ЗАО 'Ликероводчный завод имени трудового красного знамени'", null, "Россия", 50000, 50000, "RUB" ),
    )
}
