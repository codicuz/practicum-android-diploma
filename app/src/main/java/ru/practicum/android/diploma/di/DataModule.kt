package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.FavoriteRepositoryImpl
import ru.practicum.android.diploma.data.datasources.FilterDataStore
import ru.practicum.android.diploma.data.datasources.LocalTeamDataSource
import ru.practicum.android.diploma.data.db.AppDataBase
import ru.practicum.android.diploma.data.db.VacanciesDao
import ru.practicum.android.diploma.data.db.VacancyDbConverter
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.data.repositories.AreasRepositoryImpl
import ru.practicum.android.diploma.data.repositories.IndustriesRepositoryImpl
import ru.practicum.android.diploma.data.repositories.TeamRepositoryImpl
import ru.practicum.android.diploma.data.repositories.VacancyRepositoryImpl
import ru.practicum.android.diploma.domain.api.FavoriteRepository
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
    single {Room.databaseBuilder(get(), AppDataBase::class.java, "database.db").build()}
    single {get<AppDataBase>().vacanciesDao()}
    single<FavoriteRepository> { FavoriteRepositoryImpl(get(), get()) }

    factory { VacancyDbConverter() }
}
