package com.example.royaal.common_android

import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {

    val isOnline: Flow<Boolean>

}

interface NetworkMonitorProvider {
    val networkMonitor: NetworkMonitor
}

val LocalNetworkMonitorProvider =
    compositionLocalOf<NetworkMonitorProvider> { error("No NetworkMonitorProvider provided") }