package ru.practicum.android.diploma.ui.navigation

import ru.practicum.android.diploma.R

sealed class Screen(
    val route: String,
    val icon: Int? = null,
    val labelRes: Int? = null
) {
    object Home : Screen("home", R.drawable.nav_bar_home, R.string.nav_bar_home)
    object Favourite : Screen("favourite", R.drawable.nav_bar_favorite, R.string.nav_bar_favorite)
    object Team : Screen("team", R.drawable.nav_bar_team, R.string.nav_bar_team)

    object Filter : Screen("filter")
    object JobLocation : Screen("job_location")
    object IndustrySelect : Screen("industry_select")
    object CountrySelect : Screen("country_select")
    object RegionSelect : Screen("region_select")
    object VacancyDetail : Screen("vacancy_detail/{vacancyId}") {
        fun createRoute(vacancyId: String): String = "vacancy_detail/$vacancyId"
    }
}
