package com.example.wembleymoviesapp.ui.view.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wembleymoviesapp.data.API.API
import com.example.wembleymoviesapp.databinding.ActivityDetailMovieBinding
import com.example.wembleymoviesapp.domain.MovieDetail
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
        val idMovie = intent.extras?.getInt("ID")

        //Find a movie
        idMovie?.let { controller.findMovie(it) }
    }

    fun bindDetailMovie(movie: MovieDetail) {
        with(movie) {
            backdrop?.let { loadImage(it, binding.imageViewBackdrop) }
            binding.textViewTitleDetail.text = title
            binding.textViewDescriptionDetail.text = overview
            binding.textViewValoration.text = "Vote: $valoration/10"

            // Establecer color del texto, dependiendo de la valoracion que tenga la pelicula
            // No funciona no se por que
            /*binding.textViewValoration.setTextColor(
                when (valoration?.toInt()) {
                    in 0..4 -> android.R.color.holo_red_dark
                    in 5..10 -> android.R.color.holo_green_dark
                    else -> android.R.color.holo_red_dark
                }
            )*/

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