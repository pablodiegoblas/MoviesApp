package com.example.wembleymoviesapp.ui.view.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.wembleymoviesapp.BuildConfig
import com.example.wembleymoviesapp.R
import com.example.wembleymoviesapp.databinding.ActivityDetailMovieBinding
import com.example.wembleymoviesapp.domain.models.MovieModel
import com.example.wembleymoviesapp.ui.viewModel.DetailMovieViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    private var binding: ActivityDetailMovieBinding? = null

    private val detailMovieViewModel: DetailMovieViewModel by viewModels()

    private val badValuation = 0..4
    private val mediumValuation = 5..6
    private val goodValuation = 7..10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Find bundle of the intent
        val idMovie = intent.extras?.getInt("ID")
        //Find a movie
        idMovie?.let { detailMovieViewModel.setMovie(it) }

        detailMovieViewModel.detailMovieModel.observe(this) {
            bindDetailMovie(it)
        }
    }

    private fun showErrorDatabase() {
        Toast.makeText(this, "ERROR FROM DATABASE", Toast.LENGTH_SHORT).show()
    }

    private fun bindDetailMovie(movieModel: MovieModel) {
        with(movieModel) {
            backdrop?.let { binding?.let { it1 -> loadImage(it, it1.imageViewBackdrop) } }
            binding?.textViewTitleDetail?.text = title
            binding?.textViewDescriptionDetail?.text = overview
            binding?.textViewValoration?.text =
                getString(R.string.textViewValuation, valuation.toString())

            // Text color, depending the movies valuation
            binding?.textViewValoration?.setTextColor(
                when (valuation?.toInt()) {
                    in badValuation -> ContextCompat.getColor(
                        applicationContext,
                        R.color.red_valuation
                    )
                    in mediumValuation -> ContextCompat.getColor(
                        applicationContext,
                        R.color.orange_valuation
                    )
                    in goodValuation -> ContextCompat.getColor(
                        applicationContext,
                        R.color.green_valuation
                    )
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
        binding = null
    }
}