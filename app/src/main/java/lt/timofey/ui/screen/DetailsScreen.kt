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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import lt.timofey.R
import lt.timofey.ui.viewmodel.NavigationViewModel

@Composable
fun DetailsScreen(
    navController: NavController,
    navigationViewModel: NavigationViewModel
) {
    val state = navigationViewModel.uiState.collectAsState()
    Text(text = state.value.toString())
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenDemo() {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.SpaceBetween
//    ) {

//    }
    Scaffold(
        topBar = { TopDetails() }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Filled.AccountBox,
                contentDescription = "",
                //modifier = Modifier
                //.fillMaxSize()
            )
            BottomDetailsBar(paddingValue = paddingValue)
        }
    }
}

@Composable
fun TopDetails() {
    Row {
        Box(
            modifier = Modifier
                .clickable { }
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
            text = "Name Surname",
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
fun BottomDetailsBar(paddingValue: PaddingValues) {
    Row(
        modifier = Modifier
            .padding(paddingValue)
            .padding(horizontal = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(20.dp))
                .align(alignment = Alignment.CenterVertically)
                .clickable {  },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.download_vector),
                contentDescription = "",

                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.tertiary,
                        shape = RoundedCornerShape(20.dp))
                    .padding(10.dp),
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
            painter = painterResource(id = R.drawable.bookmark_button_inactive),
        )
    }
}