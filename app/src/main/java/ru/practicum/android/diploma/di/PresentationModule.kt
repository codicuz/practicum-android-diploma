package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.favorite.FavoriteViewModel
import ru.practicum.android.diploma.presentation.filter.FilterViewModel
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.presentation.team.TeamViewModel
import ru.practicum.android.diploma.presentation.vacancy.VacancyViewModel

val presentationModule = module {
    viewModel { TeamViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { FavoriteViewModel() }
    viewModel { FilterViewModel(get()) }
    viewModel { VacancyViewModel() }
}
