package com.example.wembleymoviesapp.data.API

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class API {

    companion object {

        const val API_KEY = "bf416be99a24b9cfe9ee3f8c57775b65"
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMG_URL = "https://image.tmdb.org/t/p/w300"

        private val interceptor = HttpLoggingInterceptor()
        private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}