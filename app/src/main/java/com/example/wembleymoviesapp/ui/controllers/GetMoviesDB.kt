package com.example.wembleymoviesapp.ui.controllers

import com.example.wembleymoviesapp.data.database.MovieDB

interface GetMoviesDB {
    fun onSuccess(moviesDB: List<MovieDB>)
    fun onError()
}