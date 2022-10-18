package com.example.mymoviesapp.ui.activities.main

import android.view.MenuItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymoviesapp.R
import com.example.mymoviesapp.handlers.PreferencesHandler
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject

enum class Destination {
    Popular,
    Favourites
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferencesHandler: PreferencesHandler
) : ViewModel(), NavigationBarView.OnItemSelectedListener {

    val navigateTo = MutableLiveData<Destination>()

    init {
        navigateTo.postValue(Destination.Popular)
        preferencesHandler.language = Locale.getDefault().language
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.optionPopular -> navigateTo.postValue(Destination.Popular)
            R.id.optionFavourites -> navigateTo.postValue(Destination.Favourites)
        }
        return true
    }

    fun loadPopularDestination() {
        navigateTo.postValue(Destination.Popular)
    }

    fun checkShowMoviesPreferences() = preferencesHandler.showChooseGenre

}