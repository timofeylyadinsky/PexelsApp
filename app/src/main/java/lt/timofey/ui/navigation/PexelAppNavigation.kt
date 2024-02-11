package lt.timofey.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import lt.timofey.ui.screen.HomeScreen

@Composable
fun PexelAppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.HomeScreen.route ) {
        composable(route = Screens.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
    }
}