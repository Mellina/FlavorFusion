package com.bassul.flavorfusion.framework.di

import com.bassul.flavorfusion.framework.network.FoodApi
import com.bassul.flavorfusion.framework.network.interceptor.AuthorizationInterceptor
import com.bassul.flavorfusion.BuildConfig
import com.bassul.flavorfusion.BuildConfig.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIMEOUT_SECONDS = 15L

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            )
        }
    }

    @Provides
    fun provideAuthorizationInterceptor(): AuthorizationInterceptor {
        return AuthorizationInterceptor(
            BuildConfig.API_KEY
        )
    }
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authorizationInterceptor: AuthorizationInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authorizationInterceptor)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): FoodApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(FoodApi::class.java)

    }

}