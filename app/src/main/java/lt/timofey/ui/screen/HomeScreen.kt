package lt.timofey.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import lt.timofey.ui.state.FeaturedCollectionsUIState
import lt.timofey.ui.viewmodel.HomeScreenViewModel

@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = homeScreenViewModel.uiState.collectAsState()
    //Text(text = "${(state.value.loadingFeaturedCollections as FeaturedCollectionsUIState.SUCCESS).featuredCollections.collections.toString()}")
}

@Composable
fun SearchBar(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {

}