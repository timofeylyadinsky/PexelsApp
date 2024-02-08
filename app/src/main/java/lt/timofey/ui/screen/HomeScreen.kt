package lt.timofey.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import lt.timofey.domain.entity.CuratedPhotos
import lt.timofey.domain.entity.Photos
import lt.timofey.ui.state.CuratedPhotosUIState
import lt.timofey.ui.viewmodel.HomeScreenViewModel

@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = homeScreenViewModel.uiState.collectAsState()
    HomeSearchBar()
    when (val curated = state.value.loadingCuratedPhotos) {
        //   PhotosCollections(navController = navController)
        CuratedPhotosUIState.LOADING -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is CuratedPhotosUIState.SUCCESS -> {
            PhotosCollections(navController = navController, curatedPhotos = curated.curatedPhotos)
        }

        is CuratedPhotosUIState.ERROR -> {
            Text(text = "failure ${(state as CuratedPhotosUIState.ERROR).message}")
        }
    }
    //Text(text = "${(state.value.loadingFeaturedCollections as FeaturedCollectionsUIState.SUCCESS).featuredCollections.collections.toString()}")
}

@Composable
fun HomeSearchBar(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {

}

@Composable
fun PhotosCollections(
    navController: NavController,
    curatedPhotos: CuratedPhotos
) {
    LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxSize()
    ) {
//        curatedPhotos.photos.forEach() {
//            PhotoItem(navController = navController, curatedPhoto = it)
//        }
        items(curatedPhotos.photos) {
            curatedPhotos.photos.forEach() {
                PhotoItem(navController = navController, curatedPhoto = it)
            }
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PhotoItem(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavController,
    curatedPhoto: Photos
) {
    Card(modifier = Modifier
        .clickable(onClick = {

        })) {
            GlideImage(model = {curatedPhoto.src.original}, contentDescription = curatedPhoto.alt)
    }
}