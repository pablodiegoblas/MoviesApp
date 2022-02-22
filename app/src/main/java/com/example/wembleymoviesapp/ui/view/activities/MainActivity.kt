package com.example.wembleymoviesapp.ui.view.activities

import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.wembleymoviesapp.R
import com.example.wembleymoviesapp.databinding.ActivityMainBinding
import com.example.wembleymoviesapp.ui.controllers.MainController
import com.example.wembleymoviesapp.ui.view.fragments.FavMoviesFragment
import com.example.wembleymoviesapp.ui.view.fragments.PopularMoviesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        val popularFragment = PopularMoviesFragment()
        val favouritesFragment = FavMoviesFragment()
    }

    private val controller: MainController = MainController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Principal fragment
        controller.replaceFragment(popularFragment)

        //Set Listeners of this view
        setListeners()
    }

    private fun setListeners() {
        val navigation = binding.bottomNavigation
        navigation.setOnItemSelectedListener(controller)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchView = menu!!.findItem(R.id.item_bar_search).actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE

        searchView.setOnQueryTextListener(controller)

        return super.onCreateOptionsMenu(menu)
    }


}