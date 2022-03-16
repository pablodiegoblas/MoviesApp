package com.example.wembleymoviesapp

import android.app.Application

class MoviesApp : Application() {

    companion object {
        var instance: Application? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this@MoviesApp
    }
}
