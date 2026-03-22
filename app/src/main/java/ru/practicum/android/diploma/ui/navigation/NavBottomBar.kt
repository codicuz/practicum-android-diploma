package ru.practicum.android.diploma.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.additionalColors

@Composable
fun NavBottomBar(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val barColors = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.additionalColors.blue,
        selectedTextColor = MaterialTheme.additionalColors.blue,
        unselectedTextColor = MaterialTheme.additionalColors.gray,
        unselectedIconColor = MaterialTheme.additionalColors.gray,
        indicatorColor = Color.Transparent,
    )

    Column {
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )


        NavigationBar(
            containerColor = MaterialTheme.colorScheme.primary
        ) {
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
}




