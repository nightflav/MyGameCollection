package com.example.royaal.datastore

import androidx.datastore.core.DataStore
import com.example.royaal.DarkThemeConfigProto
import com.example.royaal.ThemeBrandProto
import com.example.royaal.UserSettings
import com.example.royaal.copy
import com.example.royaal.core.common.model.DarkThemeConfiguration
import com.example.royaal.core.common.model.ThemeBrandConf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataStore @Inject constructor(
    private val userPreferences: DataStore<UserSettings>,
) {

    val userSettings = userPreferences.data.map {
        com.example.royaal.core.common.model.UserSettings(
            useDynamicColors = it.useDynamicColor,
            isInDarkTheme = when (it.darkThemeConfig) {
                null,
                DarkThemeConfigProto.DARK_THEME_CONFIG_UNSPECIFIED,
                DarkThemeConfigProto.UNRECOGNIZED,
                DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM,
                ->
                    DarkThemeConfiguration.FOLLOW_ANDROID

                DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT ->
                    DarkThemeConfiguration.LIGHT

                DarkThemeConfigProto.DARK_THEME_CONFIG_DARK -> DarkThemeConfiguration.DARK
            },
            themeBrandConf = when (it.themeBrand) {
                null,
                ThemeBrandProto.THEME_BRAND_UNSPECIFIED,
                ThemeBrandProto.UNRECOGNIZED,
                ThemeBrandProto.THEME_BRAND_DEFAULT,
                -> ThemeBrandConf.DEFAULT

                ThemeBrandProto.THEME_BRAND_ANDROID -> ThemeBrandConf.ANDROID
            }
        )
    }

    suspend fun setThemeBrand(themeBrandConf: ThemeBrandConf) {
        userPreferences.updateData {
            it.copy {
                this.themeBrand = when (themeBrandConf) {
                    ThemeBrandConf.DEFAULT -> ThemeBrandProto.THEME_BRAND_DEFAULT
                    ThemeBrandConf.ANDROID -> ThemeBrandProto.THEME_BRAND_ANDROID
                }
            }
        }
    }

    suspend fun setDarkThemeConfig(darkThemeConfiguration: DarkThemeConfiguration) {
        userPreferences.updateData {
            it.copy {
                this.darkThemeConfig = when (darkThemeConfiguration) {
                    DarkThemeConfiguration.DARK -> DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
                    DarkThemeConfiguration.FOLLOW_ANDROID -> DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM
                    DarkThemeConfiguration.LIGHT -> DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT
                }
            }
        }
    }

    suspend fun setUseDynamicColor(useDynamicColor: Boolean) {
        userPreferences.updateData {
            it.copy {
                this.useDynamicColor = useDynamicColor
            }
        }
    }
}