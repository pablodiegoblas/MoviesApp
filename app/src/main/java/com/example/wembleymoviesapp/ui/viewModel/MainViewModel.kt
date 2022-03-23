package com.example.wembleymoviesapp.ui.viewModel

import android.view.MenuItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wembleymoviesapp.R
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


enum class Destination {
    Popular,
    Favourites
}

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel(), NavigationBarView.OnItemSelectedListener {

    val navigateTo = MutableLiveData<Destination>()

    fun initialMain() {
        navigateTo.postValue(Destination.Popular)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.optionPopular -> navigateTo.postValue(Destination.Popular)
            R.id.optionFavourites -> navigateTo.postValue(Destination.Favourites)
        }
        return true
    }
}