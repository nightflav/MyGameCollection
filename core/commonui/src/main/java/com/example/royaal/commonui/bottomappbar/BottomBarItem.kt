package com.example.royaal.commonui.bottomappbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.royaal.core.common.navigation.TopLevelDestinations

sealed class BottomBarItem(
    val name: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
) {
    data object Home : BottomBarItem(
        name = "Home",
        selectedIcon = Icons.Default.Home,
        unselectedIcon = Icons.Outlined.Home,
        route = TopLevelDestinations.Home.route
    )

    data object Favourite : BottomBarItem(
        name = "Favourite",
        selectedIcon = Icons.Default.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder,
        route = TopLevelDestinations.Fav.route
    )

    data object Explore : BottomBarItem(
        name = "Explore",
        selectedIcon = Icons.Default.Explore,
        unselectedIcon = Icons.Outlined.Explore,
        route = TopLevelDestinations.Exp.route
    )
}
