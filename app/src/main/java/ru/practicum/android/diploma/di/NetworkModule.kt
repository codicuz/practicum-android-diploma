package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.util.NetworkConnectivityChecker

val networkModule = module {
    single {
        NetworkConnectivityChecker(context = get())
    }
}
