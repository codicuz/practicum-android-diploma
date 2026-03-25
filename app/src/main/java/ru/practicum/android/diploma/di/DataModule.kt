package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.datasources.FilterDataStore
import ru.practicum.android.diploma.data.datasources.LocalTeamDataSource
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.data.repositories.AreasRepositoryImpl
import ru.practicum.android.diploma.data.repositories.IndustriesRepositoryImpl
import ru.practicum.android.diploma.data.repositories.TeamRepositoryImpl
import ru.practicum.android.diploma.data.repositories.VacancyRepositoryImpl
import ru.practicum.android.diploma.domain.api.VacancyRepository
import ru.practicum.android.diploma.domain.models.AreasRepository
import ru.practicum.android.diploma.domain.models.IndustriesRepository
import ru.practicum.android.diploma.domain.models.TeamRepository

val dataModule = module {
    single { LocalTeamDataSource() }
    single<TeamRepository> { TeamRepositoryImpl(get()) }
    single<VacancyRepository> { VacancyRepositoryImpl(get()) }
    single<NetworkClient> { RetrofitNetworkClient(get()) }
    single<AreasRepository> { AreasRepositoryImpl(get()) }
    single<IndustriesRepository> { IndustriesRepositoryImpl(get()) }
    single { FilterDataStore(get()) }
}
