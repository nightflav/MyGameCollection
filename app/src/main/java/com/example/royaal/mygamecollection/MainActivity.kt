package com.example.royaal.mygamecollection

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.royaal.api.FavouriteFeatureEntry
import com.example.royaal.api.HomeEntry
import com.example.royaal.api.LocalGameDetailsProvider
import com.example.royaal.api.LocalGamesRepositoryProvider
import com.example.royaal.api.LocalLocalGamesRepositoryProvider
import com.example.royaal.commonui.bottomappbar.BottomGamesBar
import com.example.royaal.core.common.model.DarkThemeConfiguration
import com.example.royaal.core.common.model.ThemeBrandConf
import com.example.royaal.core.database.di.LocalDatabaseProvider
import com.example.royaal.core.network.di.LocalNetworkProvider
import com.example.royaal.data.LocalDataProvider
import com.example.royaal.feature_settings.SettingsViewModel
import com.example.royaal.mygamecollection.navigation.MainNavHost
import com.example.royaal.mygamecollection.ui.theme.AppTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController
    private lateinit var viewModelFactory: MainActivityViewModel.Factory
    private lateinit var settingsViewModelFactory: SettingsViewModel.Factory

    private val viewModel: MainActivityViewModel by assistedViewModel {
        viewModelFactory.create(it)
    }
    private val settingsViewModel: SettingsViewModel by assistedViewModel {
        settingsViewModelFactory.create(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        hideBottomNavigationBar()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach {
                        uiState = it
                    }
                    .collect()
            }
        }

        val appComponent = (this.application as BaseApp).appComponent
        viewModelFactory = appComponent.viewModelFactory
        settingsViewModelFactory = appComponent.settingsViewModelFactory

        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                MainActivityUiState.Loading -> true
                is MainActivityUiState.Success -> false
            }
        }

        setContent {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(
                    lightScrim, darkScrim
                )
            )
            val darkTheme = shouldUseDarkTheme(uiState)
            DisposableEffect(darkTheme) {
                onDispose {}
            }
            val navController = rememberNavController()
            val destinations = appComponent.destinations
            AppTheme(
                useDarkTheme = darkTheme,
                dynamicColor = shouldDisableDynamicTheming(uiState = uiState)
            ) {
                navHostController = navController
                CompositionLocalProvider(
                    LocalNetworkProvider provides appComponent,
                    LocalDatabaseProvider provides appComponent,
                    LocalGamesRepositoryProvider provides appComponent,
                    LocalGameDetailsProvider provides appComponent,
                    LocalDataProvider provides appComponent,
                    LocalLocalGamesRepositoryProvider provides appComponent
                ) {
                    Surface {
                        Scaffold(
                            bottomBar = {
                                BottomGamesBar(
                                    navController = navController,
                                    destinations = destinations.filter {
                                        it.key == HomeEntry::class.java ||
                                                it.key == FavouriteFeatureEntry::class.java
                                    }
                                )
                            }
                        ) {
                            MainNavHost(
                                navController = navController,
                                settingsViewModel = settingsViewModel,
                                destinations = destinations,
                                paddingValues = it
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun shouldUseAndroidTheme(
        uiState: MainActivityUiState,
    ): Boolean = when (uiState) {
        MainActivityUiState.Loading -> false
        is MainActivityUiState.Success -> when (uiState.userSettings.themeBrandConf) {
            ThemeBrandConf.DEFAULT -> false
            ThemeBrandConf.ANDROID -> true
        }
    }

    @Composable
    private fun shouldDisableDynamicTheming(
        uiState: MainActivityUiState,
    ): Boolean = when (uiState) {
        MainActivityUiState.Loading -> false
        is MainActivityUiState.Success -> !uiState.userSettings.useDynamicColors
    }

    @Composable
    private fun shouldUseDarkTheme(
        uiState: MainActivityUiState,
    ): Boolean = when (uiState) {
        MainActivityUiState.Loading -> isSystemInDarkTheme()
        is MainActivityUiState.Success -> when (uiState.userSettings.isInDarkTheme) {
            DarkThemeConfiguration.FOLLOW_ANDROID -> isSystemInDarkTheme()
            DarkThemeConfiguration.LIGHT -> false
            DarkThemeConfiguration.DARK -> true
        }
    }

    private val lightScrim = Color.argb(0xe6, 0xFF, 0xFF, 0xFF)
    private val darkScrim = Color.argb(0x80, 0x1b, 0x1b, 0x1b)

    private fun hideBottomNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            val controller = window.insetsController
            if (controller != null) {
                controller.hide(WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
    }

}