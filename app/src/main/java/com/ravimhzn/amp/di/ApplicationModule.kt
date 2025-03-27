package com.ravimhzn.amp.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.ravimhzn.amp.util.NetworkUtil
import com.ravimhzn.amp.BaseApplication
import com.ravimhzn.amp.environment.EnvReader
import com.ravimhzn.amp.network.ApiService
import com.ravimhzn.amp.network.NetworkInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication =
        app.applicationContext as BaseApplication

    @Singleton
    @Provides
    fun provideNetworkUtil(@ApplicationContext context: Context) = NetworkUtil(context)

    @Singleton
    @Provides
    fun provideRetrofit(
        @ApplicationContext context: Context,
        envReader: EnvReader
    ): Retrofit = Retrofit.Builder()
        .baseUrl(envReader.config.baseUrl)
        .client(createClient(context, envReader))
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()

    private fun createClient(
        @ApplicationContext context: Context,
        envReader: EnvReader
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .readTimeout(envReader.config.timeout.toLong(), TimeUnit.SECONDS)
            .connectTimeout(envReader.config.timeout.toLong(), TimeUnit.SECONDS)
            .addInterceptor(NetworkInterceptor())
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}