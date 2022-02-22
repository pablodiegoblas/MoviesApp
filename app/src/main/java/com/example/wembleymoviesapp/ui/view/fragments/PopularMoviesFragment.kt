package com.example.wembleymoviesapp.ui.view.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wembleymoviesapp.data.db.DBMoviesProvider
import com.example.wembleymoviesapp.data.model.MovieModel
import com.example.wembleymoviesapp.data.model.NetworkMoviesProvider
import com.example.wembleymoviesapp.databinding.FragmentPopularMoviesBinding
import com.example.wembleymoviesapp.ui.view.activities.DetailMovieActivity
import com.example.wembleymoviesapp.ui.view.adapters.PopularMoviesAdapter

class PopularMoviesFragment() : Fragment() {

    private var _binding: FragmentPopularMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbProvider: DBMoviesProvider

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dbProvider = DBMoviesProvider(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setContext(this.requireContext())

        _binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Loading the popular movies from the API
        NetworkMoviesProvider.getAllPopularMoviesRequest(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        //IMPORTANTE DESTRUIR ACTIVIDAD
        _binding = null

        dbProvider.destroy()
    }

    fun createAdapter(list: List<MovieModel>): Unit {
        if (list.isEmpty()) binding.tvPopularDefault.visibility = View.VISIBLE
        else {
            //Put DefaultText Gone
            binding.tvPopularDefault.visibility = View.GONE

            //Charge the adapter
            binding.recyclerViewPopularMovies.layoutManager = LinearLayoutManager(this.context)
            val adapter = PopularMoviesAdapter(list, dbProvider)
            binding.recyclerViewPopularMovies.adapter = adapter
        }
    }

    fun showErrorAPI() {
        Toast.makeText(this.requireContext(), "Connection failure", Toast.LENGTH_SHORT).show()
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