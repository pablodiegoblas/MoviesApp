package com.example.wembleymoviesapp.data.database.dao

import androidx.room.*
import com.example.wembleymoviesapp.data.database.entities.MovieEntity

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies_table WHERE favourite = '1'")
    suspend fun getAllFavouritesMovies(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Update(entity = MovieEntity::class)
    suspend fun updateMovie(movie: MovieEntity)

    @Query("SELECT * FROM movies_table WHERE id = :idMovie")
    suspend fun findMovie(idMovie: Int) : MovieEntity
}