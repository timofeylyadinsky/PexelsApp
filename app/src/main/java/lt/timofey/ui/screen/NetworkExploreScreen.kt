package lt.timofey.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import lt.timofey.R
import lt.timofey.ui.viewmodel.HomeScreenViewModel

@Preview(showBackground = true)
@Composable
fun NetworkConnectionStub(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {
    val state = homeScreenViewModel.uiState.collectAsState()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Icon(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.no_network_icon),
                contentDescription = "Connection Error"
            )
            Text(
                text = "Try Again",
                modifier = Modifier
                    .clickable {
                        if (state.value.isSearched) {
                            homeScreenViewModel.onSearchTextChange(state.value.searchQuery)
                        } else {
                            homeScreenViewModel.updateNetwork()
                        }
                    }
                    .fillMaxWidth(),
                style = TextStyle(
                    fontSize = 24.sp,
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}