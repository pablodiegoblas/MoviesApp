package com.example.wembleymoviesapp.ui.view.activities

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
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
        controller.createDB()

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
            binding.textViewValoration.text = "Vote: $valuation/10"

            // Establecer color del texto, dependiendo de la valoracion que tenga la pelicula
            binding.textViewValoration.setTextColor(
                when (valuation?.toInt()) {
                    in 0..4 -> Color.RED
                    in 5..7 -> Color.YELLOW
                    in 8..10 -> Color.GREEN
                    else -> Color.RED
                }
            )

        }

    }

    private fun loadImage(url: String, imageView: ImageView) {
        Picasso.get().load("${API.IMG_URL}$url").fit().into(imageView)
    }

    override fun onDestroy() {
        super.onDestroy()
        controller.destroyDB()
        _binding = null
    }
}