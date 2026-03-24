package ru.practicum.android.diploma.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.practicum.android.diploma.ui.favorite.FavoriteScreen
import ru.practicum.android.diploma.ui.search.FilterScreen
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
                SearchScreen(
                    onFilterClick = {
                        navController.navigate(Screen.Filter.route)
                    }
                )
            }

            composable(Screen.Filter.route) { _ ->
                FilterScreen(
                    navController = navController,
                    onJobLocationClick = {
                        navController.navigate(Screen.JobLocation.route)
                    },
                    onIndustryClick = {
                        navController.navigate(Screen.IndustrySelect.route)
                    },
                    onNavigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
        composable(route = Screen.Favourite.route) {
            FavoriteScreen()
        }
        composable(route = Screen.Team.route) {
            TeamScreen()
        }
    }
}
