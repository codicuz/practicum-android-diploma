package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.team.TeamViewModel

val presentationModule = module {
    viewModel { TeamViewModel(get()) }

}
