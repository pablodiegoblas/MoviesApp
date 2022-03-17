package com.example.wembleymoviesapp.ui.viewModel

import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wembleymoviesapp.R
import com.example.wembleymoviesapp.ui.view.fragments.FavMoviesFragment
import com.example.wembleymoviesapp.ui.view.fragments.PopularMoviesFragment
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel(), NavigationBarView.OnItemSelectedListener {

    val navigateTo = MutableLiveData<Fragment>()

    fun initialMain() {
        navigateTo.postValue(PopularMoviesFragment())
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.optionPopular -> navigateTo.postValue(PopularMoviesFragment())
            R.id.optionFavourites -> navigateTo.postValue(FavMoviesFragment())
        }
        return true
    }
}