package lt.timofey.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import lt.timofey.R
import lt.timofey.ui.state.LoadingPhotoUIState
import lt.timofey.ui.util.downloader.DownloaderImpl
import lt.timofey.ui.viewmodel.DetailsScreenViewModel
import lt.timofey.ui.viewmodel.NavigationViewModel

@Composable
fun DetailsScreen(
    navController: NavController,
    navigationViewModel: NavigationViewModel,
    detailsScreenViewModel: DetailsScreenViewModel = hiltViewModel()
) {
    //val state = navigationViewModel.uiState.collectAsState()
    val state = detailsScreenViewModel.uiState.collectAsState()
    when (val loaded = state.value.loadingPhoto) {
        LoadingPhotoUIState.LOADING -> {
            LoadingBar()
        }

        is LoadingPhotoUIState.SUCCESS -> {
            Scaffold(
                modifier = Modifier.padding(20.dp),
                topBar = { TopDetails(loaded.photos.photographer, navController) },
            ) { paddingValue ->
                Column(
                    modifier = Modifier
                        .padding(paddingValue)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    AsyncImage(
                        model = loaded.photos.src.original,
                        contentDescription = loaded.photos.alt,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(10.dp))
                            .fillMaxWidth()
                            .padding(top = 20.dp, bottom = 10.dp)
                            .clickable {}
                    )
                    BottomDetailsBar(paddingValue = paddingValue)
                }
            }
        }

        is LoadingPhotoUIState.ERROR -> {}
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DetailsScreenDemo() {
//    Scaffold(
//        modifier = Modifier.padding(20.dp),
//        topBar = { TopDetails("text") }
//    ) { paddingValue ->
//        Column(
//            modifier = Modifier
//                .padding(paddingValue)
//                .fillMaxSize()
//        ) {
//            Icon(
//                imageVector = Icons.Filled.AccountBox,
//                contentDescription = "",
//                modifier = Modifier.padding(vertical = 10.dp)
//                //.fillMaxSize()
//            )
//            BottomDetailsBar(paddingValue = paddingValue)
//        }
//    }
//}

@Composable
fun TopDetails(
    text: String,
    navController: NavController
) {
    Row {
        Box(
            modifier = Modifier
                .clickable {
                    navController.popBackStack()
                }
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(10.dp)
                )
                .height(40.dp)
                .width(40.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "back",
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp)
                    .align(Alignment.Center)
            )
        }
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.CenterVertically),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BottomDetailsBar(
    paddingValue: PaddingValues,
    detailsScreenViewModel: DetailsScreenViewModel = hiltViewModel()
) {
    val state = detailsScreenViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val downloader = DownloaderImpl(context)
    Row(
        modifier = Modifier
            //.padding(paddingValue)
            //.padding(horizontal = 15.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(20.dp)
                )
                .align(alignment = Alignment.CenterVertically)
                .clickable {
                    downloader.downloadImage(state.value.photo?.src?.original)
                },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.download_vector),
                contentDescription = "",

                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.tertiary,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(15.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = "Download",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(horizontal = 10.dp)
            )
        }
        Image(
            contentDescription = "",
            painter = painterResource(
                id = if (state.value.isSaved) R.drawable.bookmark_button_active
                else R.drawable.bookmark_button_inactive,
            ),
            modifier = Modifier.clickable {
                detailsScreenViewModel.clickBookmarkButton()
            }
        )
    }
}