package com.example.wembleymoviesapp.ui.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.wembleymoviesapp.R
import com.example.wembleymoviesapp.databinding.FragmentPopularMoviesBinding
import com.example.wembleymoviesapp.domain.MovieItem
import com.example.wembleymoviesapp.ui.view.activities.DetailMovieActivity
import com.example.wembleymoviesapp.ui.view.adapters.PopularMoviesAdapter
import com.example.wembleymoviesapp.ui.viewModel.PopularViewModel
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

        swipeRefreshLayout = binding?.swipePopulars
        setListeners()

        // Loading the popular movies from the API
        popularViewModel.returnAllPopularMovies()
    }

    private fun setListeners() {
        //Each time that refreshing recharge the popular movies
        swipeRefreshLayout?.setOnRefreshListener(popularViewModel)

        //Observer the view model
        // If the list is not empty update the movies adapter
        // Else show no movies text
        popularViewModel.popularMovieModel.observe(viewLifecycleOwner) {
            if (popularViewModel.popularMovieModel.value?.isNotEmpty() == true) {
                updatePopularMoviesAdapter(it)
            }
            else showNotMoviesText()

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
                val intent = Intent(context, DetailMovieActivity::class.java).apply {
                    putExtra("ID", it.id)
                }
                startActivity(intent)
            },
            {
                popularViewModel.pressFavButton(it)
            },
            {
                sharedInfo("¿Te apetece venir a ver conmigo la pelicula ${it.title}?")
            }
        )

        binding?.recyclerViewPopularMovies?.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = popularMoviesAdapter
        }

    }

    private fun updatePopularMoviesAdapter(items: List<MovieItem>) {
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

    /**
     * Override this method for create with others options the menu in this fragment
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        val searchView = menu.findItem(R.id.item_bar_search).actionView as SearchView

        // SearchView Listeners
        searchView.setOnQueryTextListener(popularViewModel)
        searchView.setOnCloseListener(popularViewModel)

        super.onCreateOptionsMenu(menu, inflater)
    }
}