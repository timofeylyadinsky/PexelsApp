package lt.timofey.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
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
    val state = rememberLazyStaggeredGridState()
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        state = state,
        modifier = Modifier
            .fillMaxSize(),
            //.verticalScroll(state = rememberScrollState())
            //.wrapContentSize(unbounded = true),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),

    ) {
        items(curatedPhotos.photos) {
            PhotoItem(navController = navController, curatedPhoto = it)
        }
    }

}

@Composable
fun PhotoItem(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavController,
    curatedPhoto: Photos
) {
    AsyncImage(
        model = curatedPhoto.src.medium,
        contentDescription = curatedPhoto.alt,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .clickable { Log.d("!!!!", curatedPhoto.toString()) },
            ///.padding(horizontal = 10.dp, vertical = 5.dp)

    )
//    GlideImage(
//        imageModel = { curatedPhoto.src.medium },
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(10.dp, 5.dp, 10.dp, 5.dp)
//            .clickable { Log.d("!!!!", curatedPhoto.toString()) }
//            .clip(
//                RoundedCornerShape(10.dp)
//            ),
//            //.fillMaxWidth(),
//        imageOptions = ImageOptions(contentScale = ContentScale.Fit),
//        requestOptions = { RequestOptions()
//            .diskCacheStrategy(DiskCacheStrategy.ALL)
//        }
//    )

}