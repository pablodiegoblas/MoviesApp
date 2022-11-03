package com.example.mymoviesapp.ui.fragments.valuation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.mymoviesapp.R
import com.example.mymoviesapp.databinding.FragmentValuationMovieDialogBinding
import com.example.mymoviesapp.domain.models.MovieModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ValuationMovieDialog : DialogFragment() {

    private var binding: FragmentValuationMovieDialogBinding? = null

    private var movieId: MovieModel? = null

    private lateinit var onSuccess: (Double) -> Unit
    private lateinit var onFailure: (Int) -> Unit

    companion object {
        fun newInstance(
            movie: MovieModel,
            onSuccess: (Double) -> Unit,
            onFailure: (Int) -> Unit
        ) = ValuationMovieDialog().apply {
            this.movieId = movie
            this.onSuccess = onSuccess
            this.onFailure = onFailure
        }
    }

    private val viewModel: ValuationMovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentValuationMovieDialogBinding.inflate(layoutInflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let {
            with(it) {
                toolbar.setNavigationOnClickListener { dismiss() }

                rateButton.setOnClickListener {
                    rateNumberInput.text.let { rateNumber ->
                        movieId?.let { movieId ->
                            viewModel.rateMovie(
                                movieId,
                                rateNumber.toString(),
                                { valuation ->
                                    onSuccess(valuation)
                                },
                                { errorText ->
                                    onFailure(errorText)
                                }
                            )
                        }
                    }
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun getTheme(): Int {
        return R.style.Dialog
    }
}