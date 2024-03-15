package com.example.royaal.core.network.di

import com.example.royaal.core.network.ApiQualifier
import com.example.royaal.core.network.BuildConfig
import com.example.royaal.core.network.common.GamesApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RawgNetworkModule {

    @Provides
    fun provideBaseUrl(): String = BuildConfig.API_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val url = chain.request().url
                .newBuilder()
                .addQueryParameter("key", BuildConfig.API_KEY)
                .build()
            val request = chain.request().newBuilder()
                .url(url)
                .build()
            chain.proceed(request)
        }
        .addInterceptor(
            HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
        return json.asConverterFactory(contentType)
    }

    @ApiQualifier
    @Singleton
    @Provides
    fun provideRetrofit(
        converterFactory: Converter.Factory,
        baseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(converterFactory)
        .client(okHttpClient)
        .build()

    @Provides
    fun provideRawgApi(
        @ApiQualifier
        retrofit: Retrofit
    ): GamesApi = retrofit.create(GamesApi::class.java)
}