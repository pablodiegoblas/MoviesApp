package com.example.wembleymoviesapp.ui.view.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wembleymoviesapp.R
import com.example.wembleymoviesapp.data.database.DBMoviesProvider
import com.example.wembleymoviesapp.data.model.RequestMovie
import com.example.wembleymoviesapp.data.server.ServerMoviesProvider
import com.example.wembleymoviesapp.databinding.FragmentFavMoviesBinding
import com.example.wembleymoviesapp.domain.MovieItem
import com.example.wembleymoviesapp.ui.controllers.FavouritesController
import com.example.wembleymoviesapp.ui.view.activities.DetailMovieActivity
import com.example.wembleymoviesapp.ui.view.adapters.FavMoviesAdapter

class FavMoviesFragment() : Fragment() {

    private var _binding: FragmentFavMoviesBinding? = null
    private val binding get() = _binding!!

    lateinit var controller: FavouritesController

    override fun onAttach(context: Context) {
        super.onAttach(context)
        controller = FavouritesController(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Recargar las opciones del menu
        setHasOptionsMenu(true)

        _binding = FragmentFavMoviesBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controller.getFavouritesMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onDestroy() {
        super.onDestroy()
        controller.destroyDB()
    }

    fun showNotMoviesFavText() {
        binding.recyclerViewFavouritesMovies.visibility = View.GONE
        binding.tvFavouriteDefaultText.visibility = View.VISIBLE
    }

    fun createAdapter(listFav: List<MovieItem>) {
        // Put visibility DetaultText Gone
        binding.tvFavouriteDefaultText.visibility = View.GONE
        binding.recyclerViewFavouritesMovies.visibility = View.VISIBLE

        // Charge the adapter
        binding.recyclerViewFavouritesMovies.layoutManager = LinearLayoutManager(this.context)
        val adapter = FavMoviesAdapter(
            listFav,
            {
                val intent: Intent = Intent(context, DetailMovieActivity::class.java).apply {
                    putExtra("ID", it.id)
                }
                startActivity(intent)
            },
            {
                controller.pressFavButton(it.first, it.second)
            },
            {
                sharedInfo("¿Te apetece venir a ver conmigo la pelicula ${it.title}?")
            }
        )

        binding.recyclerViewFavouritesMovies.adapter = adapter
    }

    private fun sharedInfo(textShare: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, textShare)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
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