package lt.timofey.ui.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import lt.timofey.R
import lt.timofey.domain.entity.CuratedPhotos
import lt.timofey.domain.entity.Photos
import lt.timofey.ui.navigation.Screens
import lt.timofey.ui.state.CuratedPhotosUIState
import lt.timofey.ui.viewmodel.HomeScreenViewModel


@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = homeScreenViewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            HomeSearchBar()
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { paddingValues ->

        //
        when (val curated = state.value.loadingCuratedPhotos) {
            CuratedPhotosUIState.LOADING -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is CuratedPhotosUIState.SUCCESS -> {
                PhotosCollections(
                    navController = navController,
                    curatedPhotos = curated.curatedPhotos,
                    paddingValues = paddingValues
                )
            }

            is CuratedPhotosUIState.ERROR -> {
                Text(text = "failure ${(state as CuratedPhotosUIState.ERROR).message}")
            }
        }
    }
}


@Composable
fun HomeSearchBar(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {
    val state = homeScreenViewModel.uiState.collectAsState()
    TextField(value = state.value.searchQuery,
        onValueChange = homeScreenViewModel::onSearchTextChange,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .padding(10.dp),
        placeholder = { Text(text = "search") }
    )
}

@Composable
fun PhotosCollections(
    navController: NavController,
    curatedPhotos: CuratedPhotos,
    paddingValues: PaddingValues
) {
    val state = rememberLazyStaggeredGridState()
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        state = state,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalItemSpacing = 10.dp,
        horizontalArrangement = Arrangement.spacedBy(10.dp),

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
            .clickable { Log.d("!!!!", curatedPhoto.toString()) }
    )
}

@SuppressLint("ResourceType")
@Composable
fun BottomBar(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val items = listOf(
        Screens.HomeScreen,
        Screens.BookmarkScreen
    )
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = { /*TODO*/ },
                icon = {
                    Image(
                        painter = (if (currentDestination?.route == screen.route) painterResource(
                            id = screen.selectedIcon)
                        else painterResource(
                            id = screen.unselectedIcon
                        )),
                        contentDescription = screen.route
                    )
                })
        }

    }
}
