package com.example.wembleymoviesapp.data.API

import com.example.wembleymoviesapp.BuildConfig
import com.example.wembleymoviesapp.data.API.interceptors.ApiKeyInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class API {

    companion object {
        private val interceptor = HttpLoggingInterceptor()
        private val interceptorApiKey = ApiKeyInterceptor()

        private val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(interceptorApiKey)
            .build()

        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl(BuildConfig.ApiBaseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}