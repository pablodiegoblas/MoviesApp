package com.example.mymoviesapp.extension

import android.view.View

fun View.visible(isVisible: Boolean, invisible: Boolean = false) {
    visibility = when {
        isVisible -> View.VISIBLE
        invisible.not() -> View.GONE
        else -> View.INVISIBLE
    }
}