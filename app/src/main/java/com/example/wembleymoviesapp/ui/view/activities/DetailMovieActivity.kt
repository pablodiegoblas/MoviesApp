package com.example.wembleymoviesapp.ui.view.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wembleymoviesapp.data.API.API
import com.example.wembleymoviesapp.data.model.DataProvider
import com.example.wembleymoviesapp.data.model.MovieModel
import com.example.wembleymoviesapp.databinding.ActivityDetailMovieBinding
import com.example.wembleymoviesapp.ui.controllers.DetailController
import com.squareup.picasso.Picasso

class DetailMovieActivity : AppCompatActivity() {

    private var _binding: ActivityDetailMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var controller: DetailController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailMovieBinding.inflate(layoutInflater)

        setContentView(binding.root)

        controller = DetailController(this)

        //Find bundle of the intent
        val title = intent.extras?.getString("title")

        //Find a movie
        title?.let { controller.findMovie(it) }
    }

    fun bindDetailMovie(movie: MovieModel?) {
        if (movie != null) {
            with(movie) {
                backdropPath?.let { loadImage(it, binding.imageViewBackdrop) }
                binding.textViewTitleDetail.text = title
                binding.textViewDescriptionDetail.text = overview
                binding.textViewValoration.text = "Vote: $voteAverage/10"
            }
        }

    }

    private fun loadImage(url: String, imageView: ImageView) {
        Picasso.get().load("${API.IMG_URL}$url").fit().into(imageView)
    }

    fun showError() {
        Toast.makeText(this, "Connection Failure", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}