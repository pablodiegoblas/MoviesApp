package com.example.wembleymoviesapp.di

import com.example.wembleymoviesapp.BuildConfig
import com.example.wembleymoviesapp.data.API.APIServices.MoviesService
import com.example.wembleymoviesapp.data.API.interceptors.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    //provide retrofit
    @Singleton
    @Provides
    fun provideRetrofit(interceptorApiKey: ApiKeyInterceptor): Retrofit {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(interceptorApiKey)
            .build()

        return Retrofit.Builder().baseUrl(BuildConfig.ApiBaseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //Create API service
    @Singleton
    @Provides
    fun provideMoviesApiService(retrofit: Retrofit): MoviesService {
        return retrofit.create(MoviesService::class.java)
    }
}