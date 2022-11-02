package com.example.mymoviesapp.ui.activities.detailMovie

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mymoviesapp.R
import com.example.mymoviesapp.databinding.ActivityDetailMovieBinding
import com.example.mymoviesapp.domain.models.MovieModel
import com.example.mymoviesapp.domain.models.MovieState
import com.example.mymoviesapp.extension.loadImage
import com.example.mymoviesapp.extension.showDialog
import com.example.mymoviesapp.extension.visible
import com.example.mymoviesapp.ui.fragments.valuation.ValuationMovieDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    private var binding: ActivityDetailMovieBinding? = null

    private val viewModel: DetailMovieViewModel by viewModels()

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
        idMovie?.let { viewModel.setMovie(it) }

        binding?.toolbar?.setNavigationOnClickListener { onBackPressed() }

        viewModel.detailMovieModel.observe(this) {
            bindDetailMovie(it)
        }

        setListeners()
    }

    private fun setListeners() {
        binding?.let {
            with(it) {
                yesChip.setOnClickListener {
                    viewModel.changeStateMovie(MovieState.See)
                    viewModel.detailMovieModel.value?.let {
                        showDialog(ValuationMovieDialog.newInstance(it))
                    }
                }
                pendingChip.setOnClickListener { viewModel.changeStateMovie(MovieState.Pending) }
                notChip.setOnClickListener { viewModel.changeStateMovie(MovieState.NotSee) }

                imageViewFavourite.setOnClickListener { viewModel.pressFavButton() }
            }
        }
    }

    private fun showErrorDatabase() {
        Toast.makeText(this, "ERROR FROM DATABASE", Toast.LENGTH_SHORT).show()
    }

    private fun bindDetailMovie(movieModel: MovieModel) {
        binding?.let { binding ->
            with(binding) {
                movieModel.backdrop?.let { imageViewBackdrop.loadImage(it) }
                textViewTitleDetail.text = movieModel.title
                textViewDescriptionDetail.text = movieModel.overview
                textViewGlobalValuation.text =
                    getString(R.string.text_view_global_valuation, movieModel.valuation.toString())

                // Text color depending the movies valuation
                 textViewGlobalValuation.setTextColor(
                    when (movieModel.valuation?.toInt()) {
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

                textViewMyValuation.text =
                    getString(R.string.text_view_personal_valuation, movieModel.personalValuation.toString())
                textViewMyValuation.visible(movieModel.personalValuation != null)

                yesChip.isChecked = movieModel.state == MovieState.See
                pendingChip.isChecked = movieModel.state == MovieState.Pending
                notChip.isChecked = movieModel.state == MovieState.NotSee

                if (movieModel.favourite)
                    imageViewFavourite.setImageResource(R.drawable.ic_favourite_red)
                else imageViewFavourite.setImageResource(R.drawable.ic_no_favourite_red)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}