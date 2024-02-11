package lt.timofey.ui.navigation

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.ui.res.painterResource
import lt.timofey.R

sealed class Screens(val route: String, val selectedIcon: Int = 0, val unselectedIcon: Int = 0) {

    data object HomeScreen : Screens("home_screen", R.drawable.home_button_active, R.drawable.home_button_inactive)
    data object BookmarkScreen : Screens("bookmark_screen", R.drawable.bookmark_button_active, R.drawable.bookmark_button_inactive)
}