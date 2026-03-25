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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.practicum.android.diploma.ui.theme.additionalColors

@Composable
fun NavBottomBar(navController: NavController) {
    val items = listOf(
        Screen.Home,
        Screen.Favourite,
        Screen.Team
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val shouldShow = currentDestination?.hierarchy?.any {
        it.route in items.map { screen -> screen.route }
    }

    shouldShow?.let { if (!it) return }

    val barColors = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.additionalColors.blue,
        selectedTextColor = MaterialTheme.additionalColors.blue,
        unselectedTextColor = MaterialTheme.additionalColors.gray,
        unselectedIconColor = MaterialTheme.additionalColors.gray,
        indicatorColor = Color.Transparent,
    )

    currentDestination?.hierarchy?.any {
        it.route in items.map { screen -> screen.route }
    }

    Column {
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )

        NavigationBar(
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            items.forEach { screen ->
                val selected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true

                NavigationBarItem(
                    colors = barColors,
                    selected = selected,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    label = {
                        Text(
                            text = stringResource(screen.labelRes!!),
                            style = MaterialTheme.typography.bodySmall,
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(screen.icon!!),
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}
