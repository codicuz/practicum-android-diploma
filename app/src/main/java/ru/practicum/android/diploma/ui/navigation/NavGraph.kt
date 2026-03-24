package ru.practicum.android.diploma.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.practicum.android.diploma.ui.favorite.FavoriteScreen
import ru.practicum.android.diploma.ui.search.SearchScreen
import ru.practicum.android.diploma.ui.team.TeamScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = "home_graph",
        modifier = modifier
    ) {
        navigation(
            route = "home_graph",
            startDestination = Screen.Home.route
        ) {
            composable(route = Screen.Home.route) {
                SearchScreen()
            }
        }
        composable(route = Screen.Favourite.route) {
            FavoriteScreen()
        }
//            composable(route = Screen.Vacancy.name) {
//                VacancyScreen()
//            }
//            composable(route = Routes.Filter.name) {
//                FilterScreen()
//            }
        composable(route = Screen.Team.route) {
            TeamScreen()
        }
    }
}
