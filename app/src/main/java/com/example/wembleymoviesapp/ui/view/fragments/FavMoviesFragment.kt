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
import com.example.wembleymoviesapp.data.db.DBMoviesProvider
import com.example.wembleymoviesapp.data.model.MovieModel
import com.example.wembleymoviesapp.data.model.NetworkMoviesProvider
import com.example.wembleymoviesapp.databinding.FragmentFavMoviesBinding
import com.example.wembleymoviesapp.ui.view.activities.DetailMovieActivity
import com.example.wembleymoviesapp.ui.view.adapters.FavMoviesAdapter

class FavMoviesFragment : Fragment() {

    private var _binding: FragmentFavMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbProvider: DBMoviesProvider

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dbProvider = DBMoviesProvider(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        setContext(this.requireContext())

        _binding = FragmentFavMoviesBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Loading data from database
        val listFav = dbProvider.getAllFavouritesMovies()

        if (listFav.isEmpty()) {
            binding.tvPopularDefault.visibility = View.VISIBLE
        } else {
            binding.tvPopularDefault.visibility = View.GONE
        }
        // Important here because don't charge the adapter
        createAdapter(listFav)
    }

    fun createAdapter(list: List<MovieModel>) {
        if (list.isEmpty()) binding.tvPopularDefault.visibility = View.VISIBLE
        else {
            //Put DefaultText Gone
            binding.tvPopularDefault.visibility = View.GONE

            //Charge the adapter
            binding.recyclerViewFavouritesMovies.layoutManager = LinearLayoutManager(this.context)
            val adapter = FavMoviesAdapter(list, dbProvider)
            binding.recyclerViewFavouritesMovies.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        dbProvider.destroy()
    }

    fun showErrorAPI() {
        Toast.makeText(this.requireContext(), "Connection failure", Toast.LENGTH_SHORT).show()
    }

    /**
     * Override this method for create with others options the menu in this fragment
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        val searchView = menu.findItem(R.id.item_bar_search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                text?.let { searchText ->
                    if (searchText != "") {
                        NetworkMoviesProvider.getMoviesSearched(this@FavMoviesFragment, searchText)
                    } else {
                        val listFav = dbProvider.getAllFavouritesMovies()
                        createAdapter(listFav)
                    }
                }

                return true
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {
        private lateinit var context: Context

        fun setContext(con: Context) {
            context = con
        }

        /**
         * Function for start the Detail Activity for show details from Movie that have be clicked
         */
        fun passDetailActivity(title: String) {
            val intent: Intent = Intent(context, DetailMovieActivity::class.java).apply {
                putExtra("title", title)
            }
            intent.putExtra("title", title)
            context.startActivity(intent)
        }

        fun sharedInfo(text: String) {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        }
    }


}