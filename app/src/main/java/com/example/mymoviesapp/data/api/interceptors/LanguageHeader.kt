package com.example.mymoviesapp.data.api.interceptors

import java.util.Locale

private const val HEADER_VALUE_LANGUAGE = "language"

class LanguageHeader(
    val key: String,
    val value: String
) {

    companion object {
        fun fromDeviceLanguage() : LanguageHeader =
            LanguageHeader(
                key = HEADER_VALUE_LANGUAGE,
                value = Locale.getDefault().language
            )
    }
}