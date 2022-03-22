package com.example.wembleymoviesapp.domain

import com.example.wembleymoviesapp.data.database.MovieDB

interface GetMovieDB {
    fun onSuccess(movieDB: MovieDB)
    fun onError()
}