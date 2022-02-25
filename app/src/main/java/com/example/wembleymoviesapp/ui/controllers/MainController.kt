package com.example.wembleymoviesapp.ui.controllers

import android.view.MenuItem
import android.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.wembleymoviesapp.R
import com.example.wembleymoviesapp.data.server.ServerMoviesProvider
import com.example.wembleymoviesapp.ui.view.activities.MainActivity
import com.google.android.material.navigation.NavigationBarView

class MainController(
    val mainActivity: MainActivity,
    private val serverMoviesProvider: ServerMoviesProvider = ServerMoviesProvider()
) : NavigationBarView.OnItemSelectedListener,
    SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.optionPopular -> replaceFragment(mainActivity.popularFragment)
            R.id.optionFavourites -> replaceFragment(mainActivity.favouritesFragment)
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
                serverMoviesProvider.getMoviesSearched(mainActivity.popularFragment, searchText, mainActivity.popularFragment.controller, null)
            } else {
                serverMoviesProvider.getAllPopularMoviesRequest(mainActivity.popularFragment.controller, null)
            }
        }

        return true
    }

    override fun onClose(): Boolean {
        serverMoviesProvider.getAllPopularMoviesRequest(mainActivity.popularFragment.controller, null)
        return true
    }
}