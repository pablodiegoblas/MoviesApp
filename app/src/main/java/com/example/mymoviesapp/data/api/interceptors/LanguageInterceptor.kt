package com.example.mymoviesapp.data.api.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * [Interceptor] that provides the default device language
 */
class LanguageInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val languageHeader = LanguageHeader.fromDeviceLanguage()

        val request = chain.request()

        val url = request.url.newBuilder()
            .addQueryParameter(languageHeader.key, languageHeader.value)
            .build()

        val newRequester = request.newBuilder()
            .url(url)
            .build()

        return chain.proceed(newRequester)
    }

}