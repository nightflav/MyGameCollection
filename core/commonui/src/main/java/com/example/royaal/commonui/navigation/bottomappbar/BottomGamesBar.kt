package com.example.royaal.commonui.navigation.bottomappbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFirstOrNull
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.royaal.commonui.Destinations
import com.example.royaal.commonui.navigation.circularbottomappbar.CircularBarRoute
import com.example.royaal.commonui.navigation.circularbottomappbar.CircularBottomAppBar

@Composable
fun BottomGamesBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    destinations: Destinations
) {
    val currRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val routes = destinations.values.map {
        CircularBarRoute(
            name = it.name,
            route = it.featureRoute,
            icon = it.selectedIcon,
            onNavigateToRoute = {
                if (it.featureRoute != currRoute) {
                    navController.navigate(it.featureRoute) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        )
    }

    AnimatedVisibility(
        currRoute in routes.map { it.route },
        exit = slideOutVertically { it },
        enter = slideInVertically { it }
    ) {
        CircularBottomAppBar(
            selectedRoute = routes.fastFirstOrNull { it.route == currRoute } ?: routes.first(),
            routes = routes,
            navBarHeight = 80.dp
        )
    }
}