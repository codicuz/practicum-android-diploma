package ru.practicum.android.diploma.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.practicum.android.diploma.ui.favorite.FavoriteScreen
import ru.practicum.android.diploma.ui.filter.CountrySelectScreen
import ru.practicum.android.diploma.ui.filter.FilterScreen
import ru.practicum.android.diploma.ui.filter.IndustrySelectScreen
import ru.practicum.android.diploma.ui.filter.JobLocationScreen
import ru.practicum.android.diploma.ui.filter.RegionSelectScreen
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
        startDestination = "home_graph",
        modifier = modifier
    ) {
        navigation(
            route = "home_graph",
            startDestination = Screen.Home.route
        ) {
            composable(route = Screen.Home.route) {
                SearchScreen(
                    onVacancyClick = { vacancyId ->
                        navController.navigate(Screen.VacancyDetail.createRoute(vacancyId))
                    },
                    onFilterClick = {
                        navController.navigate(Screen.Filter.route)
                    }
                )
            }

            composable(
                route = Screen.VacancyDetail.route,
                arguments = listOf(
                    navArgument("vacancyId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val vacancyId = backStackEntry.arguments?.getString("vacancyId") ?: ""
                VacancyScreen(
                    vacancyId = vacancyId,
                    onNavigateBack = { navController.navigateUp() }
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

            composable(Screen.JobLocation.route) { _ ->
                JobLocationScreen(
                    navController = navController,
                    onNavigateBack = { navController.navigateUp() },
                    onJobCountryClick = {
                        navController.navigate(Screen.CountrySelect.route)
                    },
                    onJobRegionClick = { countryId ->
                        navController.currentBackStackEntry?.savedStateHandle?.set("country_id", countryId)
                        navController.navigate(Screen.RegionSelect.route)
                    }
                )
            }

            composable(Screen.CountrySelect.route) { _ ->
                CountrySelectScreen(
                    onNavigateBack = { navController.navigateUp() },
                    onCountrySelected = { countryId, countryName ->
                        navController.previousBackStackEntry?.savedStateHandle?.apply {
                            set("selected_country_id", countryId)
                            set("selected_country_name", countryName)
                        }
                        navController.navigateUp()
                    }
                )
            }

            composable(Screen.RegionSelect.route) { _ ->
                val jobLocationEntry = navController.previousBackStackEntry
                val countryId = jobLocationEntry?.savedStateHandle?.get<Int>("country_id")

                RegionSelectScreen(
                    navController = navController,
                    countryId = countryId,
                    onNavigateBack = { navController.navigateUp() },
                    onRegionSelected = { regionId, regionName ->
                        navController.previousBackStackEntry?.savedStateHandle?.apply {
                            set("selected_region_id", regionId)
                            set("selected_region_name", regionName)
                        }
                        navController.navigateUp()
                    }
                )
            }

            composable(Screen.IndustrySelect.route) {
                IndustrySelectScreen(
                    onNavigateBack = { navController.navigateUp() },
                    onIndustryConfirmed = { industryId, industryName ->
                        navController.previousBackStackEntry?.savedStateHandle?.apply {
                            set("selected_industry_id", industryId)
                            set("selected_industry_name", industryName)
                        }
                        navController.navigateUp()
                    }
                )
            }
        }

        composable(route = Screen.Favourite.route) {
            FavoriteScreen(
                onVacancyClick = { vacancyId ->
                    navController.navigate(Screen.VacancyDetail.createRoute(vacancyId))
                },
            )
        }

        composable(route = Screen.Team.route) {
            TeamScreen()
        }
    }
}
