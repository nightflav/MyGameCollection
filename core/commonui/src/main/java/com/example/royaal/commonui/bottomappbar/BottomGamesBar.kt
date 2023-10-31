package com.example.royaal.commonui.bottomappbar

import androidx.compose.animation.AnimatedVisibility
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
import com.example.royaal.commonui.Destinations

@Composable
fun BottomGamesBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    destinations: Destinations
) {
    val options = destinations.values.map {
        BottomBarItem(
            name = it.name,
            route = it.featureRoute,
            selectedIcon = it.selectedIcon!!,
            unselectedIcon = it.unselectedIcon!!,
        )
    }
    val routes = remember { options.map { it.route } }
    val currRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    AnimatedVisibility(currRoute in routes) {
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

private fun <F, S, R> Collection<F>.mergeMap(
    mergeWith: Collection<S>,
    block: (F, S) -> R
): List<R> {
    val result = mutableListOf<R>()
    val smallest = if (size < mergeWith.size) size else mergeWith.size
    val first = take(smallest)
    val second = mergeWith.take(smallest)
    for (i in 1..smallest) {
        result.add(block(first[i - 1], second[i - 1]))
    }
    return result
}