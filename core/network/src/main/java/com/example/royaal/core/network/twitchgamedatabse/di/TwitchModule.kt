package com.example.royaal.core.network.twitchgamedatabse.di

import com.example.royaal.core.network.BuildConfig
import com.example.royaal.core.network.twitchgamedatabse.TwitchApi
import com.example.royaal.core.network.twitchgamedatabse.TwitchWrapperApi
import com.example.royaal.core.network.twitchgamedatabse.TwitchWrapperApiImpl
import com.example.royaal.core.network.twitchgamedatabse.auth.TokenRefresher
import com.example.royaal.core.network.twitchgamedatabse.auth.TwitchAuth
import com.example.royaal.core.network.twitchgamedatabse.auth.TwitchAuthenticator
import com.example.royaal.core.network.twitchgamedatabse.auth.TwitchTokenRefresher
import com.example.royaal.core.network.twitchgamedatabse.qualifiers.TwitchApiUrlQualifier
import com.example.royaal.core.network.twitchgamedatabse.qualifiers.TwitchAuthRetrofit
import com.example.royaal.core.network.twitchgamedatabse.qualifiers.TwitchAuthUrlQualifier
import com.example.royaal.core.network.twitchgamedatabse.qualifiers.TwitchHttpClient
import com.example.royaal.core.network.twitchgamedatabse.qualifiers.TwitchIdQualifier
import com.example.royaal.core.network.twitchgamedatabse.qualifiers.TwitchRetrofit
import com.example.royaal.core.network.twitchgamedatabse.qualifiers.TwitchSecretQualifier
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter.Factory
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
interface TwitchModule {

    @Binds
    fun bindTwitchAuthenticator(impl: TwitchAuthenticator): Authenticator

    @Binds
    fun bindTwitchTokenRefresher(impl: TwitchTokenRefresher): TokenRefresher

    @Singleton
    @Binds
    fun bindTwitchWrapperApi(impl: TwitchWrapperApiImpl): TwitchWrapperApi

    companion object {

        @TwitchIdQualifier
        @Provides
        fun provideTwitchId(): String = BuildConfig.TWITCH_CLIENT_ID

        @TwitchSecretQualifier
        @Provides
        fun provideTwitchSecret(): String = BuildConfig.TWITCH_SECRET_KEY

        @TwitchAuthUrlQualifier
        @Provides
        fun provideTwitchAuthUrl(): String = BuildConfig.TWITCH_AUTH_URL

        @TwitchApiUrlQualifier
        @Provides
        fun provideTwitchApiUrl(): String = BuildConfig.TWITCH_API_URL

        @TwitchHttpClient
        @Singleton
        @Provides
        fun provideOkHttpClient(
            authenticator: Authenticator,
        ): OkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .authenticator(authenticator)
            .build()

        @TwitchRetrofit
        @Singleton
        @Provides
        fun provideTwitchRetrofit(
            @TwitchApiUrlQualifier
            baseUrl: String,
            @TwitchHttpClient
            okHttpClient: OkHttpClient,
            converterFactory: Factory
        ): Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()

        @Singleton
        @Provides
        fun provideTwitchApi(
            @TwitchRetrofit
            retrofit: Retrofit
        ): TwitchApi = retrofit.create(TwitchApi::class.java)

        @Singleton
        @TwitchAuthRetrofit
        @Provides
        fun provideTwitchAuthRetrofit(
            @TwitchAuthUrlQualifier
            baseUrl: String,
            converterFactory: Factory
        ): Retrofit = Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .baseUrl(baseUrl)
            .build()

        @Singleton
        @Provides
        fun provideTwitchAuthApi(
            @TwitchAuthRetrofit
            retrofit: Retrofit
        ): TwitchAuth = retrofit.create(TwitchAuth::class.java)
    }

}