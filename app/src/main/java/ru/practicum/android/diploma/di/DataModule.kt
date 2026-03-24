package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.datasources.FilterDataStore
import ru.practicum.android.diploma.data.datasources.LocalTeamDataSource
import ru.practicum.android.diploma.data.repositories.TeamRepositoryImpl
import ru.practicum.android.diploma.data.repositories.VacancyRepositoryImpl
import ru.practicum.android.diploma.domain.api.VacancyRepository
import ru.practicum.android.diploma.domain.models.TeamRepository

val dataModule = module {
    single { LocalTeamDataSource() }
    single<TeamRepository> { TeamRepositoryImpl(get()) }
    single<VacancyRepository> { VacancyRepositoryImpl(get()) }
    single { FilterDataStore(get()) }
}
