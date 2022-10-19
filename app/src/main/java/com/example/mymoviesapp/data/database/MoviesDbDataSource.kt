package com.example.mymoviesapp.data.database

import com.example.mymoviesapp.data.database.dao.GenresDao
import com.example.mymoviesapp.data.database.dao.MoviesDao
import com.example.mymoviesapp.data.mappers.toDomain
import com.example.mymoviesapp.data.mappers.toDomainModel
import com.example.mymoviesapp.data.mappers.toEntity
import com.example.mymoviesapp.domain.models.GenreMovie
import com.example.mymoviesapp.domain.models.MovieModel
import javax.inject.Inject

class MoviesDbDataSource @Inject constructor(
    private val moviesDao: MoviesDao,
    private val genresDao: GenresDao
) {

    suspend fun getAllFavouritesMovies(): List<MovieModel> {
        return moviesDao.getAllFavouritesMovies().map { it.toDomainModel() }
    }

    suspend fun insertAll(movieModels: List<MovieModel>) {
        moviesDao.insertAll(movieModels.map { it.toEntity() })
    }

    suspend fun updateMovie(movieModel: MovieModel) {
        moviesDao.updateMovie(movieModel.toEntity())
    }

    suspend fun findMovie(idMovie: Int) : MovieModel {
        return moviesDao.findMovie(idMovie).toDomainModel()
    }

    suspend fun getSelectedGenres(): List<GenreMovie> {
        return genresDao.getSelectedGenres().map { it.toDomain() }
    }

    suspend fun insertSelectedGenres(genres: List<GenreMovie>) {
        genresDao.insertGenres(genres = genres.map { it.toEntity() })
    }

    suspend fun deleteGenres() {
        genresDao.deleteGenres()
    }
}