package lt.timofey.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import lt.timofey.ui.viewmodel.NavigationViewModel

@Composable
fun DetailsScreen(
    navController: NavController,
    navigationViewModel: NavigationViewModel
) {
    val state = navigationViewModel.uiState.collectAsState()
    Text(text = state.value.toString())
}