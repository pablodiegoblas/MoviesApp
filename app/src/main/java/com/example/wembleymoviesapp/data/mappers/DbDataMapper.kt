package com.example.wembleymoviesapp.data.mappers

import com.example.wembleymoviesapp.data.database.entities.MovieEntity
import com.example.wembleymoviesapp.domain.MovieDetail
import com.example.wembleymoviesapp.domain.MovieItem


fun List<MovieEntity>.convertListMovieEntityToDomainMovieItem() =
    this.mapIndexed { _, movieDB ->
        movieDB.convertMovieEntityToDomainMovieItem()
    }

private fun MovieEntity.convertMovieEntityToDomainMovieItem() = with(this) {
    MovieItem(id, title, poster, favourite)
}

fun MovieEntity.convertToDomainMovieDetail() = with(this) {
    MovieDetail(title, backdrop, overview, voteAverage)
}

