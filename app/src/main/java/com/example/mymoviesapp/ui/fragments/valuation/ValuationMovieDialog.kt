package com.example.mymoviesapp.ui.fragments.valuation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.mymoviesapp.R
import com.example.mymoviesapp.databinding.FragmentValuationMovieDialogBinding

class ValuationMovieDialog : DialogFragment() {

    private var binding: FragmentValuationMovieDialogBinding? = null

    companion object {
        fun newInstance() = ValuationMovieDialog()
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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun getTheme(): Int {
        return R.style.Dialog
    }
}