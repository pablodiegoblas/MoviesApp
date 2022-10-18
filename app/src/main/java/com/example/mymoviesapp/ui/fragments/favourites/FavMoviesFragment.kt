package com.example.mymoviesapp.ui.fragments.favourites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymoviesapp.R
import com.example.mymoviesapp.databinding.FragmentFavMoviesBinding
import com.example.mymoviesapp.domain.models.MovieModel
import com.example.mymoviesapp.ui.activities.detailMovie.DetailMovieActivity
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

        initSearchBar()
        setListeners()

        favouritesViewModel.returnFavouritesMovies()
    }

    private fun initSearchBar() {
        with(binding) {
            this?.searchBar?.showAdditionalAction(false)
            this?.searchBar?.onAfterTextChanged { favouritesViewModel.onQueryTextSubmit(it) }
        }
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
                sharedInfo(getString(R.string.shared_movie_text, it.title))
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

}