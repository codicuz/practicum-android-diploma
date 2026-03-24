package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.repositories.VacancyRepositoryImpl
import ru.practicum.android.diploma.domain.api.VacancyInteractor
import ru.practicum.android.diploma.domain.api.VacancyRepository
import ru.practicum.android.diploma.domain.impl.VacancyInteractorImpl
import ru.practicum.android.diploma.domain.usecases.GetTeamMemberUseCase

val domainModule = module {
    factory { GetTeamMemberUseCase(get()) }
    single<VacancyInteractor> { VacancyInteractorImpl(get()) }  // ← get() найдёт VacancyRepository из dataModule

}
