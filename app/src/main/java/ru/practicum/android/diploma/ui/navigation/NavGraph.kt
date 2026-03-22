package ru.practicum.android.diploma.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.practicum.android.diploma.ui.favorite.FavoriteScreen
import ru.practicum.android.diploma.ui.filter.FilterScreen
import ru.practicum.android.diploma.ui.search.SearchScreen
import ru.practicum.android.diploma.ui.team.TeamScreen
import ru.practicum.android.diploma.ui.vacancy.VacancyScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home.name,
        modifier = modifier
    ) {
        composable(route = Routes.Home.name) {
            SearchScreen()
        }
        composable(route = Routes.Favorite.name) {
            FavoriteScreen()
        }
        composable(route = Routes.Vacancy.name) {
            VacancyScreen()
        }
        composable(route = Routes.Filter.name) {
            FilterScreen()
        }
        composable(route = Routes.Team.name) {
            TeamScreen()
        }
    }
}

enum class Routes {
    Home,
    Favorite,
    Filter,
    Vacancy,
    Team,
}
