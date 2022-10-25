package com.example.mymoviesapp.handlers

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

interface PreferencesHandler {
    var showChooseGenre: Boolean
    var language: String
    var guestSessionId: String
}

class PreferencesHandlerImpl(context: Context) : PreferencesHandler {
    private val sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

    private val edit = sharedPreferences.edit()

    fun clear() {
        edit.clear().apply()
    }

    companion object {
        private const val SHOW_CHOOSE_GENRE = "show_choose_genre"
        private const val SELECTED_LANGUAGE = "selected_language"
        private const val GUEST_SESSION_ID = "guest_session_id"
    }

    override var showChooseGenre: Boolean
        get() = sharedPreferences.getBoolean(SHOW_CHOOSE_GENRE, true)
        set(value) {
            this.edit?.putBoolean(SHOW_CHOOSE_GENRE, value)?.apply()
        }

    override var language: String
        get() = sharedPreferences.getString(SELECTED_LANGUAGE, "").orEmpty()
        set(value) {
            this.edit?.putString(SELECTED_LANGUAGE, value)?.apply()
        }

    override var guestSessionId: String
        get() = sharedPreferences.getString(GUEST_SESSION_ID, "").orEmpty()
        set(value) {
            this.edit?.putString(GUEST_SESSION_ID, value)?.apply()
        }
}