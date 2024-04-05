package com.example.royaal.mygamecollection.di

import com.example.royaal.common_android.ConnectivityNetworkMonitor
import com.example.royaal.common_android.NetworkMonitor
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface SystemModule {

    @Singleton
    @Binds
    fun bindConnectivityMonitor(impl: ConnectivityNetworkMonitor): NetworkMonitor
}