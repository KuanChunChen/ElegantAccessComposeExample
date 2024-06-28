package elegant.access.compose.example.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import elegant.access.compose.example.data.system.AppConfig
import elegant.access.compose.example.infra.openai.OpenAIService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
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
object ServiceModule {

//    @Provides
//    fun provideLoginService(
//        @Named("un-auth")
//        okHttpClient: OkHttpClient,
//        @Named("default")
//        retrofitBuilder: Retrofit.Builder,
//        appConfig: AppConfig,
//    ): FeedbackUcService {
//        return retrofitBuilder.client(okHttpClient)
//            .baseUrl(appConfig.airDroidUcServerUrl)
//            .build()
//            .create<FeedbackUcService>()
//    }

    @Provides
    fun provideOpenAIService(
        @Named("un-auth")
        okHttpClient: OkHttpClient,
        @Named("default")
        retrofitBuilder: Retrofit.Builder,
        appConfig: AppConfig,
    ): OpenAIService {

        return retrofitBuilder.client(okHttpClient)
            .baseUrl(appConfig.openAIUrl)
            .build()
            .create<OpenAIService>()
    }
}