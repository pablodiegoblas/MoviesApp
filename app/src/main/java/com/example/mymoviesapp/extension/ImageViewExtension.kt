package com.example.mymoviesapp.extension

import android.widget.ImageView
import com.example.mymoviesapp.BuildConfig
import com.squareup.picasso.Picasso

fun ImageView.loadImage(url: String) =
    Picasso.get().load("${BuildConfig.ApiImagesUrl}$url").fit().into(this)