package com.example.mymoviesapp.ui.activities.main

import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mymoviesapp.R
import com.example.mymoviesapp.databinding.ActivityMainBinding
import com.example.mymoviesapp.extension.showDialog
import com.example.mymoviesapp.ui.fragments.favourites.FavMoviesFragment
import com.example.mymoviesapp.ui.fragments.selectionFavourites.PreferencesMoviesDialog
import com.example.mymoviesapp.ui.fragments.popular.PopularMoviesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity() : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        //Observe the live data for the changes change the fragment
        viewModel.navigateTo.observe(this) {
            when (it) {
                Destination.Popular -> replaceFragment(PopularMoviesFragment())
                Destination.Favourites -> replaceFragment(FavMoviesFragment())
            }
        }

        //Set Listeners of this view
        setListeners()

        if(viewModel.checkShowMoviesPreferences()) {
            showDialog(PreferencesMoviesDialog.newInstance() {
                viewModel.loadPopularDestination()
            })
        }
    }

    private fun setListeners() {
        val navigation = binding?.bottomNavigation
        navigation?.setOnItemSelectedListener(viewModel)
    }

    private fun replaceFragment(fragment: Fragment) {
        val transition = supportFragmentManager.beginTransaction()
        transition.replace(R.id.frame_container, fragment)
        transition.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchView = menu.findItem(R.id.item_bar_search).actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        searchView.queryHint = "Search a Movie"

        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}