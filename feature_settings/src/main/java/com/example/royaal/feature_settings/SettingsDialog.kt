package com.example.royaal.feature_settings

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.royaal.core.common.model.DarkThemeConfiguration
import com.example.royaal.core.common.model.ThemeBrandConf
import kotlinx.coroutines.launch

@Composable
fun SettingsDialog(
    viewModel: SettingsViewModel,
    onDismiss: () -> Unit
) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()
    val settingsScope = rememberCoroutineScope()
    SettingsDialog(
        settingsUiState = state,
        onDismiss = onDismiss,
        onChangeThemeBrand = {
            settingsScope.launch {
                viewModel.eventQueue.send(
                    SettingsViewModel.SettingsEvents.UpdateThemeBrand(
                        it
                    )
                )
            }
        },
        onChangeDynamicColorPreference = {
            settingsScope.launch {
                viewModel.eventQueue.send(
                    SettingsViewModel.SettingsEvents.UpdateUseDynamicColor(
                        it
                    )
                )
            }
        },
        onChangeDarkThemeConfig = {
            settingsScope.launch {
                viewModel.eventQueue.send(
                    SettingsViewModel.SettingsEvents.UpdateDarkThemeConfig(
                        it
                    )
                )
            }
        }
    )
}

@Composable
private fun SettingsDialog(
    settingsUiState: SettingsScreenUiState,
    onDismiss: () -> Unit,
    onChangeThemeBrand: (themeBrand: ThemeBrandConf) -> Unit,
    onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfiguration) -> Unit,
) {
    val conf = LocalConfiguration.current
    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.widthIn(max = (conf.screenWidthDp * 0.8).dp),
        title = {
            Text(
                text = stringResource(id = R.string.settings_title),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        },
        text = {
            when (settingsUiState) {
                SettingsScreenUiState.Loading -> {
                    Text(
                        text = stringResource(id = R.string.settings_loading),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(0.6f)
                    )
                }

                is SettingsScreenUiState.Success -> SettingsDialogContent(
                    state = settingsUiState.state,
                    onChangeDarkThemeConfig = onChangeDarkThemeConfig,
                    onChangeDynamicColorPreference = onChangeDynamicColorPreference,
                    onChangeThemeBrand = onChangeThemeBrand
                )
            }
        },
        confirmButton = {
            Text(
                text = stringResource(R.string.dismiss_dialog_button_text).uppercase(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { onDismiss() },
            )
        },
        onDismissRequest = onDismiss
    )
}

@Composable
private fun SettingsDialogContent(
    state: SettingsScreenState,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfiguration) -> Unit,
    onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
    onChangeThemeBrand: (themeBrand: ThemeBrandConf) -> Unit,
) {
    Column {
        ThemeBrandSettings(
            state.themeBrand,
            onChangeThemeBrand
        )
        DarkThemeSettings(
            state.darkThemeConf,
            onChangeDarkThemeConfig
        )
        DynamicColorsSettings(
            state.useDynamicColor,
            onChangeDynamicColorPreference
        ) { supportsDynamicTheming() && state.themeBrand == ThemeBrandConf.DEFAULT }
    }
}

@Composable
private fun DynamicColorsSettings(
    useDynamicColor: Boolean,
    onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
    canBeUsed: () -> Boolean
) {
    val isEnabled = canBeUsed()
    Text(
        text = stringResource(id = R.string.use_dynamic_color),
        style = MaterialTheme.typography.titleSmall
    )
    Column(
        modifier = Modifier.alpha(if (isEnabled) 1f else 0.5f)
    ) {
        SelectableRow(
            title = "Yes",
            isSelected = useDynamicColor,
            enabled = isEnabled
        ) { onChangeDynamicColorPreference(true) }
        SelectableRow(
            title = "No",
            isSelected = !useDynamicColor,
            enabled = isEnabled
        ) { onChangeDynamicColorPreference(false) }
    }
}

@Composable
private fun DarkThemeSettings(
    darkThemeConfig: DarkThemeConfiguration,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfiguration) -> Unit,
) {
    Text(
        text = stringResource(id = R.string.use_dark_theme),
        style = MaterialTheme.typography.titleSmall
    )
    Column {
        SelectableRow(
            title = "Yes",
            isSelected = darkThemeConfig == DarkThemeConfiguration.DARK
        ) { onChangeDarkThemeConfig(DarkThemeConfiguration.DARK) }
        SelectableRow(
            title = "No",
            isSelected = darkThemeConfig == DarkThemeConfiguration.LIGHT,
        ) { onChangeDarkThemeConfig(DarkThemeConfiguration.LIGHT) }
        SelectableRow(
            title = "System",
            isSelected = darkThemeConfig == DarkThemeConfiguration.FOLLOW_ANDROID
        ) { onChangeDarkThemeConfig(DarkThemeConfiguration.FOLLOW_ANDROID) }
    }
}

@Composable
private fun ThemeBrandSettings(
    themeBrand: ThemeBrandConf,
    onChangeThemeBrand: (themeBrand: ThemeBrandConf) -> Unit
) {
    Text(
        text = stringResource(id = R.string.select_theme),
        style = MaterialTheme.typography.titleSmall
    )
    Column {
        SelectableRow(
            title = "Android",
            onSelect = { onChangeThemeBrand(ThemeBrandConf.ANDROID) },
            isSelected = themeBrand == ThemeBrandConf.ANDROID
        )
        SelectableRow(
            title = "Default",
            onSelect = { onChangeThemeBrand(ThemeBrandConf.DEFAULT) },
            isSelected = themeBrand == ThemeBrandConf.DEFAULT
        )
    }
}

@Composable
private fun ColumnScope.SelectableRow(
    title: String,
    isSelected: Boolean,
    enabled: Boolean = true,
    onSelect: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(16.dp)
            .align(CenterHorizontally)
            .selectable(
                selected = isSelected,
                onClick = onSelect,
                role = Role.RadioButton,
                enabled = enabled
            )
    ) {
        Text(text = title)
        Spacer(modifier = Modifier.weight(1f))
        RadioButton(selected = isSelected, onClick = null)
    }
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
private fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S