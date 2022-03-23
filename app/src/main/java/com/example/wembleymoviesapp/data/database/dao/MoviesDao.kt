package com.example.wembleymoviesapp.data.database.dao

import androidx.room.*
import com.example.wembleymoviesapp.data.database.entities.MovieEntity

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies_table WHERE favourite = '1'")
    fun getAllFavouritesMovies(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(movies: List<MovieEntity>)

    /*@Update(entity = MovieEntity::class)
    fun updateFavourite(movie: MovieEntity)*/

    @Query("UPDATE movies_table SET favourite = :fav WHERE id = :idMovie")
    fun updateFavourite(idMovie: Int, fav: Int)

    @Query("SELECT * FROM movies_table WHERE id = :idMovie")
    fun findMovie(idMovie: Int) : MovieEntity
}