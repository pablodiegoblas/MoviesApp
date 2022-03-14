package com.example.wembleymoviesapp.data.API.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val apiKeyHeader = ApiKeyQuery.fromApiKey()

        val request = chain.request()

        val url = request.url.newBuilder()
            .addQueryParameter(apiKeyHeader.key, apiKeyHeader.value)
            .build()

        val newRequest = request.newBuilder()
            .url(url)
            .build()

        return chain.proceed(newRequest)
    }
}