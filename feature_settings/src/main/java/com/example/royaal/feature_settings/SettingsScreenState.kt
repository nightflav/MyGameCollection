package com.example.royaal.feature_settings

import com.example.royaal.core.common.model.DarkThemeConfiguration
import com.example.royaal.core.common.model.ThemeBrandConf

data class SettingsScreenState(
    val darkThemeConf: DarkThemeConfiguration,
    val useDynamicColor: Boolean,
    val themeBrand: ThemeBrandConf,
)

sealed class SettingsScreenUiState {
    data object Loading : SettingsScreenUiState()
    data class Success(val state: SettingsScreenState) : SettingsScreenUiState()
}
