package com.example.wembleymoviesapp.data.mappers

import com.example.wembleymoviesapp.data.database.MovieDB
import com.example.wembleymoviesapp.domain.MovieDetail
import com.example.wembleymoviesapp.domain.MovieItem


fun List<MovieDB>.convertListToDomainMovieItem() =
    this.mapIndexed { _, movieDB ->
        movieDB.convertToDomainMovieItem()
    }

private fun MovieDB.convertToDomainMovieItem() = with(this) {
    MovieItem(id, title, poster, favourite)
}

fun MovieDB.convertToDomainMovieDetail() = with(this) {
    MovieDetail(title, backdrop, overview, voteAverage)
}
