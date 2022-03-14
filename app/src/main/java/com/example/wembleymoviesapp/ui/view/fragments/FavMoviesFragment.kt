package com.example.wembleymoviesapp.ui.view.fragments

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.wembleymoviesapp.R
import com.example.wembleymoviesapp.databinding.FragmentFavMoviesBinding
import com.example.wembleymoviesapp.domain.MovieItem
import com.example.wembleymoviesapp.ui.controllers.FavouritesController
import com.example.wembleymoviesapp.ui.view.activities.DetailMovieActivity
import com.example.wembleymoviesapp.ui.view.activities.MainActivity
import com.example.wembleymoviesapp.ui.view.adapters.FavMoviesAdapter

class FavMoviesFragment : Fragment() {

    private var binding: FragmentFavMoviesBinding? = null

    private lateinit var controller: FavouritesController

    var swipeRefreshLayout: SwipeRefreshLayout? = null
    private lateinit var favMoviesAdapter: FavMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = FavouritesController(this)
        controller.createDB()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFavMoviesBinding.inflate(layoutInflater, container, false).also {
        // Recargar opciones del menu
        setHasOptionsMenu(true)

        binding = it

        // Create adapter in fragment
        createAdapter()
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout = binding?.swipe
        setListeners()

        controller.getFavouritesMovies()
    }

    private fun setListeners() {
        swipeRefreshLayout?.setOnRefreshListener {
            // Each time that refreshing recharge the favourites movies
            controller.getFavouritesMovies()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // Implementado para la salida y reactivacion del Fragment
    override fun onResume() {
        super.onResume()
        controller.createDB()
    }

    override fun onPause() {
        super.onPause()
        controller.destroyDB()
    }

    fun showNotMoviesFavText() {
        binding?.recyclerViewFavouritesMovies?.visibility = View.GONE
        binding?.tvFavouriteDefaultText?.visibility = View.VISIBLE
    }

    private fun createAdapter() {
        // Put visibility DetaultText Gone
        binding?.tvFavouriteDefaultText?.visibility = View.GONE
        binding?.recyclerViewFavouritesMovies?.visibility = View.VISIBLE

        // Charge the adapter
        favMoviesAdapter = FavMoviesAdapter(
            {
                val intent: Intent = Intent(context, DetailMovieActivity::class.java).apply {
                    putExtra("ID", it.id)
                }
                startActivity(intent)
            },
            {
                controller.pressFavButton(it)
            },
            {
                sharedInfo("¿Te apetece venir a ver conmigo la pelicula ${it.title}?")
            }
        )

        binding?.recyclerViewFavouritesMovies?.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = favMoviesAdapter
        }
    }

    fun updateFavouritesMoviesAdapter(items: List<MovieItem>) {
        favMoviesAdapter.submitList(items)
    }

    private fun sharedInfo(textShare: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, textShare)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "SHARE THE MOVIE")
        startActivity(shareIntent)
    }

    /**
     * Override this method for create with others options the menu in this fragment
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        val searchView = menu.findItem(R.id.item_bar_search).actionView as SearchView

        // SearchView Listeners
        searchView.setOnQueryTextListener(controller)

        super.onCreateOptionsMenu(menu, inflater)
    }

}