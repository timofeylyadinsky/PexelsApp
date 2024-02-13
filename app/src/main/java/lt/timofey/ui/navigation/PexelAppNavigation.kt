package lt.timofey.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import lt.timofey.ui.screen.DetailsScreen
import lt.timofey.ui.screen.HomeScreen
import lt.timofey.ui.viewmodel.NavigationViewModel

@Composable
fun PexelAppNavigation(
    navigationViewModel: NavigationViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val state = navigationViewModel.uiState.collectAsState()
    NavHost(navController = navController, startDestination = Screens.HomeScreen.route ) {
        composable(route = Screens.HomeScreen.route) {
            HomeScreen(navController = navController, navigationViewModel = navigationViewModel)
        }
        composable(route = Screens.DetailsScreen.route) {
            DetailsScreen(navController = navController, navigationViewModel = navigationViewModel)
        }
    }
}

