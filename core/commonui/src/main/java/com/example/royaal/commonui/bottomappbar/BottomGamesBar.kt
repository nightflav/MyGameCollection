package com.example.royaal.commonui.bottomappbar

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomGamesBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    options: List<BottomBarItem> = listOf(
        BottomBarItem.Home,
        BottomBarItem.Favourite,
        BottomBarItem.Explore,
    ),
) {
    val routes = remember { options.map { it.route } }
    val currRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    if (currRoute in routes) {
        NavigationBar(
            modifier = modifier.navigationBarsPadding(),
        ) {
            options.forEach { option ->
                val isSelected = option.route == currRoute
                NavigationBarItem(
                    icon = {
                        Icon(
                            if (isSelected) option.selectedIcon else option.unselectedIcon,
                            option.name
                        )
                    },
                    label = {
                        Text(text = option.name)
                    },
                    selected = isSelected,
                    onClick = {
                        if (option.route != currRoute) {
                            navController.navigate(option.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    modifier = Modifier.navigationBarsPadding()
                )
            }
        }
    }
}