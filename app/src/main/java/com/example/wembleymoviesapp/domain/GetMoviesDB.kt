package com.example.wembleymoviesapp.domain

import com.example.wembleymoviesapp.data.database.MovieDB

interface GetMoviesDB {
    fun onSuccess(moviesDB: List<MovieDB>)
    fun onError()
}