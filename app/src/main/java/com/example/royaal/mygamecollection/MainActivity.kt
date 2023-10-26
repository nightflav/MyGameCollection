package com.example.royaal.mygamecollection

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.royaal.api.LocalGameDetailsProvider
import com.example.royaal.api.LocalGamesRepositoryProvider
import com.example.royaal.core.common.model.DarkThemeConfiguration
import com.example.royaal.core.common.model.ThemeBrandConf
import com.example.royaal.core.database.di.LocalDatabaseProvider
import com.example.royaal.core.network.di.LocalNetworkProvider
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
    private val baseApp
        get() = (this.application as BaseApp)

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
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
        enableEdgeToEdge()

        setContent {
            val darkTheme = shouldUseDarkTheme(uiState)
            DisposableEffect(darkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                    ) { darkTheme },
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim,
                        darkScrim,
                    ) { darkTheme },
                )
                onDispose {}
            }
            val navController = rememberNavController()
            var showSettings by remember { mutableStateOf(false) }
            AppTheme(
                useDarkTheme = darkTheme,
                dynamicColor = shouldDisableDynamicTheming(uiState = uiState)
            ) {
                navHostController = navController
                CompositionLocalProvider(
                    LocalNetworkProvider provides baseApp.appComponent,
                    LocalDatabaseProvider provides baseApp.appComponent,
                    LocalGameDetailsProvider provides baseApp.appComponent,
                    LocalGamesRepositoryProvider provides baseApp.appComponent
                ) {
                    Surface {
                        MainNavHost(
                            navController = navController
                        )
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
}