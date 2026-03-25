package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.api.VacancyInteractor
import ru.practicum.android.diploma.domain.impl.VacancyInteractorImpl
import ru.practicum.android.diploma.domain.usecases.GetAreasUseCase
import ru.practicum.android.diploma.domain.usecases.GetIndustriesUseCase
import ru.practicum.android.diploma.domain.usecases.GetTeamMemberUseCase

val domainModule = module {
    factory { GetTeamMemberUseCase(get()) }
    single<VacancyInteractor> { VacancyInteractorImpl(get()) }
    factory { GetAreasUseCase(get()) }
    factory { GetIndustriesUseCase(get()) }
}
