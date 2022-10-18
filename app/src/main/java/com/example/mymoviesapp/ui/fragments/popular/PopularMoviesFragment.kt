package com.example.mymoviesapp.ui.fragments.popular

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mymoviesapp.R
import com.example.mymoviesapp.databinding.FragmentPopularMoviesBinding
import com.example.mymoviesapp.domain.models.MovieModel
import com.example.mymoviesapp.ui.activities.detailMovie.DetailMovieActivity
import com.example.mymoviesapp.ui.fragments.selectionFavourites.PreferencesMoviesDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMoviesFragment : Fragment() {

    private var binding: FragmentPopularMoviesBinding? = null

    private val popularViewModel: PopularViewModel by viewModels()

    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentPopularMoviesBinding.inflate(layoutInflater, container, false).also {
        //Recharge options menu
        setHasOptionsMenu(true)

        binding = it

        //Create adapter
        createAdapter()

    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSearchBar()

        swipeRefreshLayout = binding?.swipePopulars
        setListeners()

        // Loading the popular movies from the API
        popularViewModel.returnAllPopularMovies()
    }


    private fun initSearchBar() {
        with(binding) {
            this?.searchBar?.setOnClickAdditionalAction {
                PreferencesMoviesDialog.newInstance() {
                    popularViewModel.returnAllPopularMovies()
                }.show(parentFragmentManager, null)
            }
            this?.searchBar?.onAfterTextChanged { popularViewModel.onQueryTextSubmit(it) }
        }
    }

    private fun setListeners() {
        // Each time that refreshing recharge the popular movies
        swipeRefreshLayout?.setOnRefreshListener(popularViewModel)

        // Observer the view model
        // If the list is not empty update the movies adapter
        // Else show no movies text
        popularViewModel.popularMovieModel.observe(viewLifecycleOwner) {
            if (popularViewModel.popularMovieModel.value?.isNotEmpty() == true) {
                updatePopularMoviesAdapter(it)
            }
            else showNotMoviesText()

            //Stop refreshing
            swipeRefreshLayout?.isRefreshing = false
        }
    }

    // Important destroy the binding here, because the lifecycle of the fragments is different
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun showNotMoviesText() {
        binding?.recyclerViewPopularMovies?.visibility = View.GONE
        binding?.tvPopularDefault?.visibility = View.VISIBLE
    }

    private fun createAdapter() {
        //Charge the adapter
        popularMoviesAdapter = PopularMoviesAdapter(
            {
                Intent(context, DetailMovieActivity::class.java).apply {
                    putExtra("ID", it.id)
                }.also {
                    startActivity(it)
                }
            },
            {
                popularViewModel.pressFavButton(it)
            },
            {
                sharedInfo(getString(R.string.shared_movie_text, it.title))
            }
        )

        binding?.recyclerViewPopularMovies?.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = popularMoviesAdapter
        }

    }

    private fun updatePopularMoviesAdapter(items: List<MovieModel>) {
        //Put visibility DefaultText Gone
        binding?.tvPopularDefault?.visibility = View.GONE
        binding?.recyclerViewPopularMovies?.visibility = View.VISIBLE

        popularMoviesAdapter.submitList(items)
    }

    private fun sharedInfo(text: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun showErrorAPI() {
        Toast.makeText(this.requireContext(), "Connection failure", Toast.LENGTH_SHORT).show()
    }
}