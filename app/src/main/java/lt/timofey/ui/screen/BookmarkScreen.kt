package lt.timofey.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import lt.timofey.domain.entity.Photos
import lt.timofey.ui.navigation.Screens
import lt.timofey.ui.state.BookmarkUIState
import lt.timofey.ui.viewmodel.BookmarkScreenViewModel

@Composable
fun BookmarkScreen(
    bookmarkScreenViewModel: BookmarkScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = bookmarkScreenViewModel.uiState.collectAsState()
    when (val response = state.value.loadingPhoto) {
        BookmarkUIState.LOADING -> {
            LoadingBar()
        }

        is BookmarkUIState.SUCCESS -> {
            Scaffold(
                bottomBar = {
                    BottomBar(navController = navController)
                },
                topBar = {
                    Text(
                        text = "Bookmark",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(7.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            ) { paddingValues ->
                BookmarkGrid(
                    navController = navController,
                    bookmarkPhotos = response.photos,
                    paddingValues = paddingValues
                )
            }
        }

        is BookmarkUIState.EMPTY -> {}
        is BookmarkUIState.ERROR -> {}

    }
}

@Composable
fun BookmarkGrid(
    navController: NavController,
    bookmarkPhotos: List<Photos>,
    paddingValues: PaddingValues
) {
    val state = rememberLazyStaggeredGridState()
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        state = state,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(5.dp),
        verticalItemSpacing = 10.dp,
        horizontalArrangement = Arrangement.spacedBy(10.dp),

        ) {
        items(bookmarkPhotos) {
            BookmarkPhotoItem(
                navController = navController,
                bookmarkPhoto = it
            )
        }
    }
}

@Composable
fun BookmarkPhotoItem(
    navController: NavController,
    bookmarkPhoto: Photos
) {
    Box(modifier = Modifier
        .clip(shape = RoundedCornerShape(10.dp))
        .fillMaxWidth()
        .clickable {
            Log.d("!!!!", bookmarkPhoto.toString())
            //navigationViewModel.setPhoto(curatedPhoto)
            navController.navigate(Screens.DetailsScreen.route + "/${bookmarkPhoto.id}")
        }) {
        AsyncImage(
            model = bookmarkPhoto.src.medium,
            contentDescription = bookmarkPhoto.alt,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(10.dp))
                .fillMaxWidth()
        )
        Text(
            text = bookmarkPhoto.photographer,
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f))
                .clip(shape = RoundedCornerShape(10.dp))
                .align(Alignment.BottomCenter)
                .padding(7.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 14.sp
        )
    }
}