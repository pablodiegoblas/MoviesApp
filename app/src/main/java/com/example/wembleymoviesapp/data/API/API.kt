package com.example.wembleymoviesapp.data.API

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class API {

    companion object {

        const val API_KEY = "bf416be99a24b9cfe9ee3f8c57775b65"
        private val BASE_URL = "https://api.themoviedb.org/3/"

        val interceptor = HttpLoggingInterceptor()
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}