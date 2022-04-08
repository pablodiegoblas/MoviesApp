package com.example.wembleymoviesapp.ui.view.activities

import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.wembleymoviesapp.R
import com.example.wembleymoviesapp.databinding.ActivityMainBinding
import com.example.wembleymoviesapp.ui.view.fragments.FavMoviesFragment
import com.example.wembleymoviesapp.ui.view.fragments.PopularMoviesFragment
import com.example.wembleymoviesapp.ui.viewModel.Destination
import com.example.wembleymoviesapp.ui.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        //Observe the live data for the changes change the fragment
        mainViewModel.navigateTo.observe(this) {
            when (it) {
                Destination.Popular -> replaceFragment(PopularMoviesFragment())
                Destination.Favourites -> replaceFragment(FavMoviesFragment())
            }
        }

        //Set Listeners of this view
        setListeners()
    }

    private fun setListeners() {
        val navigation = binding?.bottomNavigation
        navigation?.setOnItemSelectedListener(mainViewModel)
    }

    private fun replaceFragment(fragment: Fragment) {
        val transition = supportFragmentManager.beginTransaction()
        transition.replace(R.id.frame_container, fragment)
        transition.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchView = menu!!.findItem(R.id.item_bar_search).actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        searchView.queryHint = "Search a Movie"

        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}