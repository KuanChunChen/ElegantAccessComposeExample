package elegant.access.compose.example.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import elegant.access.compose.example.BuildConfig
import elegant.access.compose.example.data.system.AppConfig
import elegant.access.compose.example.data.system.SettingDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.sse.EventSource
import okhttp3.sse.EventSources
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Named

/**
 * This file is part of an Android project developed by elegant.access.
 *
 * For more information about this project, you can visit our website:
 * {@link https://elegantaccess.org}
 *
 * Please note that this project is for educational purposes only and is not intended
 * for use in production environments.
 *
 * @author Willy.Chen
 * @version 1.0.0
 * @since 2020~2024
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Named("kotlinx.serializer")
    @Provides
    fun provideKotlinxJsonConverter(): Converter.Factory {
        return Json.asConverterFactory("application/json; charset=UTF8".toMediaType())
    }

    @Provides
    fun provideAppConfig(
        settingDataStore: SettingDataStore
    ): AppConfig {
        return runBlocking { settingDataStore.appConfigFlow().first() }
    }

    @Named("default")
    @Provides
    fun provideBaseRetrofitBuilder(
        json: Json
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    }
}

@Module
@InstallIn(SingletonComponent::class)
object UnauthorizedNetworkModule {
    @Named("un-auth")
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                }
            }
            .build()
    }

    @Provides
    fun provideEventSourceFactory(@Named("un-auth") okHttpClient: OkHttpClient): EventSource.Factory {
        return EventSources.createFactory(okHttpClient)
    }
}