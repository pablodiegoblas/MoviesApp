package com.example.mymoviesapp.extension

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Extension to connect this [Activity] with its [ViewBinding]
 */
fun <T : ViewBinding> Activity.viewBinding(inflate: (LayoutInflater) -> T) = lazy {
    inflate(layoutInflater)
}

/**
 * Extension to connect this [Fragment] with its [ViewBinding]
 */
fun <T : ViewBinding> Fragment.viewBinding(inflate: (LayoutInflater) -> T) = lazy {
    inflate(layoutInflater)
}

/**
 * Extension to connect this [View] with its [ViewBinding]
 */
fun <T : ViewBinding> ViewGroup.viewBindingMerge(inflate: (LayoutInflater, ViewGroup) -> T) =
    lazyOf(inflate(LayoutInflater.from(context), this))

fun <T : ViewBinding> ViewGroup.viewBinding(inflate: (LayoutInflater, ViewGroup, Boolean) -> T) =
    lazyOf(inflate(LayoutInflater.from(context), this, true))
