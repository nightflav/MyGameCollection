package com.example.royaal.feature_settings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.royaal.core.common.model.DarkThemeConfiguration
import com.example.royaal.core.common.model.ThemeBrandConf
import com.example.royaal.core.database.app.GamesDao
import com.example.royaal.data.UserSettingsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel @AssistedInject constructor(
    private val userSettingsRepository: UserSettingsRepository,
    private val gamesDao: GamesDao,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {

    val screenState = userSettingsRepository.getUserSettings().map {
        SettingsScreenUiState.Success(
            SettingsScreenState(
                darkThemeConf = it.isInDarkTheme,
                useDynamicColor = it.useDynamicColors,
                themeBrand = it.themeBrandConf
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = SettingsScreenUiState.Loading
    )

    fun sendEvent(event: SettingsEvents) {
        viewModelScope.launch {
            when (event) {
                is SettingsEvents.UpdateDarkThemeConfig -> {
                    updateDarkThemeConfiguration(event.newDarkThemeConfiguration)
                }

                is SettingsEvents.UpdateThemeBrand -> {
                    updateThemeBrand(event.newThemeBrandConf)
                }

                is SettingsEvents.UpdateUseDynamicColor -> {
                    updateUseDynamicColor(event.shouldUse)
                }

                SettingsEvents.ClearUnusedCache -> {
                    gamesDao.clearCache()
                }
            }
        }
    }

    sealed interface SettingsEvents {
        data class UpdateDarkThemeConfig(val newDarkThemeConfiguration: DarkThemeConfiguration) :
            SettingsEvents

        data class UpdateUseDynamicColor(val shouldUse: Boolean) : SettingsEvents
        data class UpdateThemeBrand(val newThemeBrandConf: ThemeBrandConf) : SettingsEvents

        data object ClearUnusedCache : SettingsEvents
    }

    private suspend fun updateDarkThemeConfiguration(newDarkThemeConfiguration: DarkThemeConfiguration) {
        userSettingsRepository.setDarkThemeConfig(newDarkThemeConfiguration)
    }

    private suspend fun updateUseDynamicColor(shouldUse: Boolean) {
        userSettingsRepository.setUseDynamicColor(shouldUse)
    }

    private suspend fun updateThemeBrand(newThemeBrandConf: ThemeBrandConf) {
        userSettingsRepository.setThemeBrand(newThemeBrandConf)
    }

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): SettingsViewModel
    }
}