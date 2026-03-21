package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.usecases.GetTeamMemberUseCase

val domainModule = module {
    factory { GetTeamMemberUseCase(get()) }
}
