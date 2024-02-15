package lt.timofey.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import lt.timofey.ui.navigation.Screens

@Composable
fun EmptyBookmarksScreen(
    navController: NavController,
    paddingValues: PaddingValues
) {
    Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
        Column {
            Text(
                text = "You haven't saved anything yet",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 7.dp),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
            Text(text = "Explore",
                modifier = Modifier
                    .clickable {
                        navController.navigate(Screens.HomeScreen.route)
                    }
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}