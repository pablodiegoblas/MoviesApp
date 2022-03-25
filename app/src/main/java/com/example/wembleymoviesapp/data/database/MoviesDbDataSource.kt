package com.example.wembleymoviesapp.data.database

import com.example.wembleymoviesapp.data.database.dao.MoviesDao
import com.example.wembleymoviesapp.data.mappers.toDomainModel
import com.example.wembleymoviesapp.data.mappers.toEntity
import com.example.wembleymoviesapp.domain.models.MovieModel
import javax.inject.Inject

class MoviesDbDataSource @Inject constructor(
    private val dao: MoviesDao
) {

    suspend fun getAllFavouritesMovies(): List<MovieModel> {
        return dao.getAllFavouritesMovies().map { it.toDomainModel() }
    }

    suspend fun insertAll(movieModels: List<MovieModel>) {
        dao.insertAll(movieModels.map { it.toEntity() })
    }

    suspend fun updateFavourite(movieModel: MovieModel) {
        dao.updateMovie(movieModel.toEntity())
    }

    suspend fun findMovie(idMovie: Int) : MovieModel {
        return dao.findMovie(idMovie).toDomainModel()
    }
}