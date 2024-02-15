package lt.timofey.ui.screen

import android.annotation.SuppressLint
import android.content.res.Resources.Theme
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import lt.timofey.domain.entity.CuratedPhotos
import lt.timofey.domain.entity.FeaturedCollections
import lt.timofey.domain.entity.Photos
import lt.timofey.ui.navigation.Screens
import lt.timofey.ui.state.CuratedPhotosUIState
import lt.timofey.ui.state.FeaturedCollectionsUIState
import lt.timofey.ui.viewmodel.HomeScreenViewModel
import lt.timofey.ui.viewmodel.NavigationViewModel


@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavController,
    navigationViewModel: NavigationViewModel
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
        Column(modifier = Modifier.padding(paddingValues = paddingValues)) {
            FeaturedCollections()
            when (val curated = state.value.loadingCuratedPhotos) {
                CuratedPhotosUIState.LOADING -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                        //CircularProgressIndicator()
                        LinearProgressIndicator(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }

                is CuratedPhotosUIState.SUCCESS -> {
                    PhotosCollections(
                        navController = navController,
                        curatedPhotos = curated.curatedPhotos,
                        navigationViewModel = navigationViewModel
                    )
                }

                is CuratedPhotosUIState.ERROR -> {
                    //Text(text = "failure ${(curated as CuratedPhotosUIState.ERROR).message}")
                    NetworkConnectionStub()
                }
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
            .clip(shape = AbsoluteRoundedCornerShape(20.dp))
            .padding(10.dp),
        placeholder = { Text(text = "search") },
        singleLine = true,
        shape = AbsoluteRoundedCornerShape(20.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondary,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
            disabledContainerColor = MaterialTheme.colorScheme.secondary,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.onPrimary,
            focusedTextColor = MaterialTheme.colorScheme.onPrimary
        ),
        trailingIcon = {
            if (state.value.isSearched)
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "clear",
                    modifier = Modifier.clickable {
                        homeScreenViewModel.onSearchTextChange("")
                    }
                )
        }
    )
}

@Composable
fun PhotosCollections(
    navController: NavController,
    curatedPhotos: CuratedPhotos,
    //paddingValues: PaddingValues
    navigationViewModel: NavigationViewModel
) {
    val state = rememberLazyStaggeredGridState()
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        state = state,
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        verticalItemSpacing = 10.dp,
        horizontalArrangement = Arrangement.spacedBy(10.dp),

        ) {
        items(curatedPhotos.photos) {
            PhotoItem(
                navController = navController,
                curatedPhoto = it,
                navigationViewModel = navigationViewModel
            )
        }
    }

}

@Composable
fun PhotoItem(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavController,
    curatedPhoto: Photos,
    navigationViewModel: NavigationViewModel
) {
    AsyncImage(
        model = curatedPhoto.src.medium,
        contentDescription = curatedPhoto.alt,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .clickable {
                Log.d("!!!!", curatedPhoto.toString())
                //navigationViewModel.setPhoto(curatedPhoto)
                navController.navigate(Screens.DetailsScreen.route + "/${curatedPhoto.id}")
            }
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
                onClick = { navController.navigate(screen.route) },
                icon = {
                    Image(
                        painter = (if (currentDestination?.route == screen.route) painterResource(
                            id = screen.selectedIcon
                        )
                        else painterResource(
                            id = screen.unselectedIcon
                        )),
                        contentDescription = screen.route
                    )
                })
        }
    }
}

@Composable
fun FeaturedCollections(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val state = homeScreenViewModel.uiState.collectAsState()
    when (val featured = state.value.loadingFeaturedCollections) {
        FeaturedCollectionsUIState.LOADING -> {
        }

        is FeaturedCollectionsUIState.SUCCESS -> {
            FeaturedCollectionList(featuredCollection = featured.featuredCollections)
        }

        is FeaturedCollectionsUIState.ERROR -> {
        }
    }
}

@Composable
fun FeaturedCollectionList(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    featuredCollection: FeaturedCollections
    //innerPaddingValues: PaddingValues
) {
    LazyRow(
        modifier = Modifier.padding(5.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(featuredCollection.collections) {
            Card(
                modifier = Modifier
                    //.height(35.dp)
                    .padding(5.dp)
                    .clip(shape = AbsoluteRoundedCornerShape(20.dp))
                    .clickable { }
                    .background(color = MaterialTheme.colorScheme.secondary),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(
                    text = it.title,
                    modifier = Modifier
                        .padding(8.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }
    }
}
