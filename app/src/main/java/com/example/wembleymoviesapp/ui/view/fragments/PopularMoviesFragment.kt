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
import com.example.wembleymoviesapp.databinding.FragmentPopularMoviesBinding
import com.example.wembleymoviesapp.domain.MovieItem
import com.example.wembleymoviesapp.ui.controllers.PopularController
import com.example.wembleymoviesapp.ui.view.activities.DetailMovieActivity
import com.example.wembleymoviesapp.ui.view.adapters.PopularMoviesAdapter

class PopularMoviesFragment : Fragment() {

    private var _binding: FragmentPopularMoviesBinding? = null
    private val binding get() = _binding!!

    lateinit var controller: PopularController

    override fun onAttach(context: Context) {
        super.onAttach(context)
        controller = PopularController(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Loading the popular movies from the API
        controller.getPopularMovies()
    }

    // Important destroy the binding here, because the lifecycle of the fragments is different
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        //IMPORTANTE DESTRUIR ACTIVIDAD
        controller.destroyDB()
    }

    fun showNotMoviesText() {
        binding.recyclerViewPopularMovies.visibility = View.GONE
        binding.tvPopularDefault.visibility = View.VISIBLE
    }

    fun createAdapter(list: List<MovieItem>) {

        //Put visibility DefaultText Gone
        binding.tvPopularDefault.visibility = View.GONE
        binding.recyclerViewPopularMovies.visibility = View.VISIBLE

        //Charge the adapter
        binding.recyclerViewPopularMovies.layoutManager = LinearLayoutManager(this.context)
        val adapter = PopularMoviesAdapter(
            list,
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
        binding.recyclerViewPopularMovies.adapter = adapter

    }

    fun sharedInfo(text: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    fun showErrorAPI() {
        Toast.makeText(this.requireContext(), "Connection failure", Toast.LENGTH_SHORT).show()
    }

}