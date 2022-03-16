package com.example.wembleymoviesapp.ui.view.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.wembleymoviesapp.BuildConfig
import com.example.wembleymoviesapp.R
import com.example.wembleymoviesapp.databinding.ActivityDetailMovieBinding
import com.example.wembleymoviesapp.domain.MovieDetail
import com.example.wembleymoviesapp.viewModel.DetailMovieViewModel
import com.squareup.picasso.Picasso

class DetailMovieActivity : AppCompatActivity() {

    private var binding: ActivityDetailMovieBinding?= null

    private val detailMovieViewModel: DetailMovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        detailMovieViewModel.createDB()

        //Find bundle of the intent
        val idMovie = intent.extras?.getInt("ID")
        //Find a movie
        idMovie?.let { detailMovieViewModel.setMovie(it) }

        detailMovieViewModel.detailMovieModel.observe(this, Observer {
            bindDetailMovie(it)
        })
    }

    fun bindDetailMovie(movie: MovieDetail) {
        with(movie) {
            backdrop?.let { binding?.let { it1 -> loadImage(it, it1.imageViewBackdrop) } }
            binding?.textViewTitleDetail?.text = title
            binding?.textViewDescriptionDetail?.text = overview
            binding?.textViewValoration?.text = "Vote: $valuation/10"

            // Text color, depending the movies valuation
            binding?.textViewValoration?.setTextColor(
                when (valuation?.toInt()) {
                    in 0..4 -> ContextCompat.getColor(applicationContext, R.color.red_valuation)
                    in 4..7 -> ContextCompat.getColor(applicationContext, R.color.orange_valuation)
                    in 7..10 -> ContextCompat.getColor(applicationContext, R.color.green_valuation)
                    else -> ContextCompat.getColor(applicationContext, R.color.red_valuation)
                }
            )
        }
    }

    private fun loadImage(url: String, imageView: ImageView) {
        Picasso.get().load("${BuildConfig.ApiImagesUrl}$url").fit().into(imageView)
    }

    override fun onDestroy() {
        super.onDestroy()
        detailMovieViewModel.destroyDB()
        binding = null
    }
}