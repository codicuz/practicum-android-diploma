package ru.practicum.android.diploma.ui.root

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import ru.practicum.android.diploma.ui.navigation.NavBottomBar
import ru.practicum.android.diploma.ui.navigation.NavGraph
import ru.practicum.android.diploma.ui.theme.Theme

class RootActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge()
        setContent {
            RootScreen()
        }
    }
}

@Composable
fun RootScreen() {
    val navController = rememberNavController()

    Theme {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.primary,
            bottomBar = {
                NavBottomBar(navController)
            }
        ) { innerPadding ->
            NavGraph(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxHeight()
            .fillMaxSize()
    ) {
        Text(text = "Hello $name!")
    }
}
