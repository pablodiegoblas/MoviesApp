package com.example.mymoviesapp.handlers

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

interface PreferencesHandler {
    var showChooseGenre: Boolean
}

class PreferencesHandlerImpl(context: Context): PreferencesHandler {
    private val sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

    private val edit = sharedPreferences.edit()

    fun clear() {
        edit.clear().apply()
    }

    companion object {
        private const val SHOW_CHOOSE_GENRE = "show_choose_genre"
    }

    override var showChooseGenre: Boolean
        get() = sharedPreferences.getBoolean(SHOW_CHOOSE_GENRE, true)
        set(value) {
            this.edit?.putBoolean(SHOW_CHOOSE_GENRE, value)?.apply()
        }

}