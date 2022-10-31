package com.example.mymoviesapp.ui.fragments.valuation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.mymoviesapp.R
import com.example.mymoviesapp.databinding.FragmentValuationMovieDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ValuationMovieDialog : DialogFragment() {

    private var binding: FragmentValuationMovieDialogBinding? = null

    private var movieId: Int? = null

    companion object {
        fun newInstance(
            movieId: Int
        ) = ValuationMovieDialog().apply {
            this.movieId = movieId
        }
    }

    private val viewModel: ValuationMovieDialogViewModel by viewModels()

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
                                rateNumber.toString().toDouble()
                            )
                        }
                        dismiss()
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