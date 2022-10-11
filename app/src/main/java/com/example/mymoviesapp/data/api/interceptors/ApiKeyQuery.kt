package com.example.mymoviesapp.data.api.interceptors

import com.example.mymoviesapp.BuildConfig

private const val QUERY_KEY_APIKEY = "api_key"

class ApiKeyQuery(
    val key: String,
    val value: String
) {

    companion object {
        fun fromApiKey() : ApiKeyQuery =
            ApiKeyQuery(
                key = QUERY_KEY_APIKEY,
                value = BuildConfig.ApiKey
            )
    }
}