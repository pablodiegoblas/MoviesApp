package com.example.wembleymoviesapp.ui.fragments.favourites

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wembleymoviesapp.R
import com.example.wembleymoviesapp.databinding.FragmentFavMoviesBinding
import com.example.wembleymoviesapp.domain.models.MovieModel
import com.example.wembleymoviesapp.ui.activities.detailMovie.DetailMovieActivity
import com.example.wembleymoviesapp.ui.view.adapters.FavMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavMoviesFragment : Fragment() {

    private var binding: FragmentFavMoviesBinding? = null

    private val favouritesViewModel: FavouritesViewModel by viewModels()

    private lateinit var favMoviesAdapter: FavMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFavMoviesBinding.inflate(layoutInflater, container, false).also {
        //Recharge options menu
        setHasOptionsMenu(true)

        binding = it

        // Create adapter in fragment
        createAdapter()
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()

        favouritesViewModel.returnFavouritesMovies()
    }

    private fun setListeners() {
        favouritesViewModel.favouritesMovieModel.observe(viewLifecycleOwner) {
            if (favouritesViewModel.favouritesMovieModel.value?.isNotEmpty() == true) {
                updateFavouritesMoviesAdapter(it)
            } else showNotMoviesFavText()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    private fun showNotMoviesFavText() {
        binding?.recyclerViewFavouritesMovies?.visibility = View.GONE
        binding?.tvFavouriteDefaultText?.visibility = View.VISIBLE
    }

    private fun createAdapter() {
        // Charge the adapter
        favMoviesAdapter = FavMoviesAdapter(
            {
                val intent: Intent = Intent(context, DetailMovieActivity::class.java).apply {
                    putExtra("ID", it.id)
                }
                startActivity(intent)
            },
            {
                favouritesViewModel.pressFavButton(it)
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

    private fun updateFavouritesMoviesAdapter(items: List<MovieModel>) {
        // Put visibility defaultText Gone
        binding?.tvFavouriteDefaultText?.visibility = View.GONE
        binding?.recyclerViewFavouritesMovies?.visibility = View.VISIBLE

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
        searchView.setOnQueryTextListener(favouritesViewModel)
        searchView.setOnCloseListener(favouritesViewModel)

        super.onCreateOptionsMenu(menu, inflater)
    }

}