package ru.practicum.android.diploma.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.practicum.android.diploma.R

@Composable
fun NavBottomBar(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val barColors = NavigationBarItemDefaults.colors(
        selectedIconColor = colorResource(R.color.nav_bar_icon_selected),
        selectedTextColor = colorResource(R.color.nav_bar_icon_selected),
        unselectedTextColor = colorResource(R.color.nav_bar_icon),
        unselectedIconColor = colorResource(R.color.nav_bar_icon),
        indicatorColor = Color.Transparent,
    )

    NavigationBar {
        NavigationBarItem(
            colors = barColors,
            selected = currentDestination?.route == Routes.Home.name,
            onClick = {
                navController.navigate(Routes.Home.name)
            },
            label = {
                Text(
                    text = stringResource(R.string.nav_bar_home),
                    style = MaterialTheme.typography.bodySmall,
                )
            },
            icon = { Icon(painter = painterResource(R.drawable.nav_bar_home), null) }
        )

        NavigationBarItem(
            colors = barColors,
            selected = currentDestination?.route == Routes.Favorite.name,
            onClick = {
                navController.navigate(Routes.Favorite.name)
            },
            label = {
                Text(
                    text = stringResource(R.string.nav_bar_favorite),
                    style = MaterialTheme.typography.bodySmall,
                )
            },
            icon = { Icon(painter = painterResource(R.drawable.nav_bar_favorite), null) }
        )

        NavigationBarItem(
            colors = barColors,
            selected = currentDestination?.route == Routes.Team.name,
            onClick = {
                navController.navigate(Routes.Team.name)
            },
            label = {
                Text(
                    text = stringResource(R.string.nav_bar_team),
                    style = MaterialTheme.typography.bodySmall,
                )
            },
            icon = { Icon(painter = painterResource(R.drawable.nav_bar_team), null) }
        )
    }
}




