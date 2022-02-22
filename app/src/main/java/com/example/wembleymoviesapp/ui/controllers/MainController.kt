package com.example.wembleymoviesapp.ui.controllers

import android.view.MenuItem
import android.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.wembleymoviesapp.R
import com.example.wembleymoviesapp.data.model.NetworkMoviesProvider
import com.example.wembleymoviesapp.ui.view.activities.MainActivity
import com.google.android.material.navigation.NavigationBarView

class MainController(val mainActivity: MainActivity): NavigationBarView.OnItemSelectedListener,
    SearchView.OnQueryTextListener {

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.optionPopular -> replaceFragment(MainActivity.popularFragment)
            R.id.optionFavourites -> replaceFragment(MainActivity.favouritesFragment)
        }

        return true
    }

    fun replaceFragment(fragment: Fragment) {
        val transition = mainActivity.supportFragmentManager.beginTransaction()
        transition.replace(R.id.frame_container, fragment)
        transition.commit()
    }

    override fun onQueryTextSubmit(text: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(text: String?): Boolean {
        text?.let { searchText ->
            if (searchText != "") {
                NetworkMoviesProvider.getMoviesSearched(MainActivity.popularFragment, searchText)
            }
            else {
                NetworkMoviesProvider.getAllPopularMoviesRequest(MainActivity.popularFragment)
            }
        }

        return true
    }
}